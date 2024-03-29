package com.example.server.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import com.example.server.config.TokenProvider;
import com.example.server.constant.Constant;
import com.example.server.dao.ContributionDao;
import com.example.server.dao.FacultyDao;
import com.example.server.dao.MagazineDao;
import com.example.server.dao.RoleDao;
import com.example.server.dao.UserDao;
import com.example.server.dto.FacebookUserDto;
import com.example.server.dto.GoogleUserDto;
import com.example.server.entity.Faculty;
import com.example.server.entity.Role;
import com.example.server.entity.User;
import com.example.server.model.request.ChangePasswordRequest;
import com.example.server.model.request.CreateAccount;
import com.example.server.model.request.LoginUser;
import com.example.server.model.request.PagingRequest;
import com.example.server.model.request.UserSearchRequest;
import com.example.server.model.response.TotalCountResponse;
import com.example.server.model.response.UserLastPageResponse;
import com.example.server.model.response.UserResponse;
import com.example.server.model.response.UserWithMostContributionResponse;
import com.example.server.service.FileService;
import com.example.server.service.RoleService;
import com.example.server.service.UserService;
import com.example.server.util.QueryCheck;
import com.example.server.util.ResponseUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.server.constant.Constant.*;
import static com.example.server.util.ResponseUtils.*;
import static com.example.server.util.SessionUtils.*;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private QueryCheck queryCheck;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private FacultyDao facultyDao;

    @Autowired
    private MagazineDao magazineDao;

    @Autowired
    private ContributionDao contributionDao;

    @Autowired(required = true)
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    ResourceBundle rb = ResourceBundle.getBundle("other_api");
    String facebook_graph_api_base = rb.getString("FACEBOOK_GRAPH_API_BASE");

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getCode()));
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findOne(String email) {
        return userDao.findByEmail(email);
    }

    // Create account by guest
    @Override
    public User saveGuestRegister(CreateAccount user) {
        try {
            User nUser = new User();
            if (userDao.findByEmail(user.getEmail()) == null) {
                nUser.setEmail(user.getEmail());
            } else {
                return null;
            }
            nUser.setCode("U" + String.format("%04d", queryCheck.GetHighestId("user")));
            nUser.setFullName(user.getFullName());
            nUser.setAddress(user.getAddress());
            nUser.setDateOfBirth(user.getDateOfBirth());
            nUser.setPhoneNumber(user.getPhoneNumber());
            nUser.setGender(user.getGender());
            nUser.setAvatar(user.getAvatar());
            nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            Role role = roleService.findByName("GUEST");
            nUser.setRole(role);
            return userDao.save(nUser);
        } catch (Exception e) {
            return null;
        }
    }

    // Create account by admin
    @Override
    public User saveRegister(CreateAccount user, MultipartFile file) {
        try {
            User nUser = new User();
            if (userDao.findByEmail(user.getEmail()) == null) {
                nUser.setEmail(user.getEmail());
            } else {
                return null;
            }
            nUser.setCode("U" + String.format("%04d", queryCheck.GetHighestId("user")));
            nUser.setFullName(user.getFullName());
            nUser.setAddress(user.getAddress());
            nUser.setDateOfBirth(user.getDateOfBirth());
            nUser.setPhoneNumber(user.getPhoneNumber());
            nUser.setGender(user.getGender());
            nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            Role role = roleService.findById(user.getRoleId());
            if (user.getFacultyId() != null) {
                Optional<Faculty> faculty = facultyDao.findById(user.getFacultyId());
                if (faculty.get() == null) {
                    return null;
                }
                if (faculty.get().getIs_deleted() == DELETED) {
                    return null;
                }
                nUser.setFaculty(faculty.get());
            }
            if (role == null || role.getIs_deleted() == DELETED) {
                return null;
            }
            nUser.setRole(role);
            String path = fileService.storeAvatar(file, nUser.getCode());
            if (path == null) {
                return null;
            }
            nUser.setAvatar(path);
            return userDao.save(nUser);
        } catch (Exception e) {
            return null;
        }
    }

    // Get non deleted users
    @Override
    public List<Object> getUserListResponse(PagingRequest pagingRequest) {
        try {
            Sort sort = responseUtils.getSortObj(pagingRequest);
            Page<User> list = userDao
                    .getNonDeletedUser(PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit(), sort));
            int lastPage = Math.round(list.getTotalElements() / pagingRequest.getLimit()
                    + ((list.getTotalElements() % pagingRequest.getLimit() == 0) ? 0 : 1));
            List<Object> object = new ArrayList<>();
            List<UserResponse> listResponse = new ArrayList<>();
            for (User user : list) {
                UserResponse userRes = new UserResponse();
                userRes.setId(user.getId());
                userRes.setRoleId(user.getRole() == null ? null : user.getRole().getId());
                userRes.setFacultyId(user.getFaculty() == null ? null : user.getFaculty().getId());
                userRes.setCode(user.getCode() == null ? "" : user.getCode());
                userRes.setFullName(user.getFullName() == null ? "" : user.getFullName());
                userRes.setRoleName(user.getRole() == null ? "" : user.getRole().getName());
                userRes.setFacultyName(user.getFaculty() == null ? "" : user.getFaculty().getName());
                userRes.setEmail(user.getEmail() == null ? "" : user.getEmail());
                userRes.setAddress(user.getAddress() == null ? "" : user.getAddress());
                userRes.setPhoneNumber(user.getPhoneNumber() == null ? null : user.getPhoneNumber());
                userRes.setDateOfBirth(dateFormat(user.getDateOfBirth()));
                userRes.setAvatar(user.getAvatar());
                listResponse.add(userRes);
            }
            object.add(listResponse);
            object.add(lastPage);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get users by search
    @Override
    public UserLastPageResponse searchUserByRoleAndFacul(UserSearchRequest userSearchRequest) {
        try {
            int offset = userSearchRequest.getPage() - 1;
            Sort sort = responseUtils.getSortObj(userSearchRequest);
            int hasDate = 0;
            if (userSearchRequest.getStartDate() != null && userSearchRequest.getEndDate() != null) {
                hasDate = 1;
            }
            Page<User> list = userDao.searchUserByRoleAndFac(userSearchRequest.getUserId(),
                    userSearchRequest.getRoleId(), userSearchRequest.getFacultyId(), userSearchRequest.getFullName(),
                    userSearchRequest.getRoleName(), userSearchRequest.getFacultyName(), userSearchRequest.getEmail(),
                    userSearchRequest.getStartDate(), userSearchRequest.getEndDate(), hasDate,
                    userSearchRequest.getGender(), userSearchRequest.getCode(),
                    PageRequest.of(offset, userSearchRequest.getLimit(), sort));

            int lastPage = Math.round(list.getTotalElements() / userSearchRequest.getLimit()
                    + ((list.getTotalElements() % userSearchRequest.getLimit() == 0) ? 0 : 1));
            UserLastPageResponse object = new UserLastPageResponse();
            List<UserResponse> listResponse = new ArrayList<>();
            for (User user : list) {
                UserResponse userRes = new UserResponse();
                userRes.setId(user.getId());
                userRes.setRoleId(user.getRole() == null ? null : user.getRole().getId());
                userRes.setFacultyId(user.getFaculty() == null ? null : user.getFaculty().getId());
                userRes.setCode(user.getCode() == null ? "" : user.getCode());
                userRes.setFullName(user.getFullName() == null ? "" : user.getFullName());
                userRes.setRoleName(user.getRole() == null ? "" : user.getRole().getName());
                userRes.setFacultyName(user.getFaculty() == null ? "" : user.getFaculty().getName());
                userRes.setEmail(user.getEmail() == null ? "" : user.getEmail());
                userRes.setAddress(user.getAddress() == null ? "" : user.getAddress());
                userRes.setPhoneNumber(user.getPhoneNumber() == null ? null : user.getPhoneNumber());
                userRes.setDateOfBirth(dateFormat(user.getDateOfBirth()));
                userRes.setGender(user.getGender());
                userRes.setIsOnline(user.getIsOnline());
                userRes.setAvatar(user.getAvatar());
                listResponse.add(userRes);
            }
            object.setLastPage(lastPage);
            object.setList(listResponse);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleteUser(Long id) {
        try {
            User user = userDao.getOne(id);
            user.setIs_deleted(Constant.DELETED);
            userDao.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean update(CreateAccount userDto, MultipartFile file) {
        try {
            User user = userDao.getOne(userDto.getId());
            if (userDto.getRoleId() != null) {
                Optional<Role> role = roleDao.findById(userDto.getRoleId());
                if (role.get() == null) {
                    return false;
                }
                user.setRole(role.get());
            }
            if (userDto.getFacultyId() != null) {
                Optional<Faculty> facult = facultyDao.findById(userDto.getFacultyId());
                if (facult.get() == null) {
                    return false;
                }
                user.setFaculty(facult.get());
            }
            user.setEmail(userDto.getEmail());
            user.setFullName(userDto.getFullName());
            user.setAddress(userDto.getAddress());
            user.setDateOfBirth(userDto.getDateOfBirth());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setGender(userDto.getGender());
            String path = fileService.storeAvatar(file, user.getCode());
            if (path == null) {
                return null;
            }

            user.setAvatar(path);
            userDao.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserResponse findById(Long id) {
        try {
            User user = userDao.getOne(id);
            if (user.getIs_deleted() == DELETED) {
                return null;
            }
            UserResponse userRes = new UserResponse();
            userRes.setId(user.getId());
            userRes.setRoleId(user.getRole() == null ? null : user.getRole().getId());
            userRes.setFacultyId(user.getFaculty() == null ? null : user.getFaculty().getId());
            userRes.setCode(user.getCode() == null ? "" : user.getCode());
            userRes.setFullName(user.getFullName() == null ? "" : user.getFullName());
            userRes.setRoleName(user.getRole() == null ? "" : user.getRole().getName());
            userRes.setFacultyName(user.getFaculty() == null ? "" : user.getFaculty().getName());
            userRes.setEmail(user.getEmail() == null ? "" : user.getEmail());
            userRes.setAddress(user.getAddress() == null ? "" : user.getAddress());
            userRes.setPhoneNumber(user.getPhoneNumber() == null ? null : user.getPhoneNumber());
            userRes.setDateOfBirth(dateFormat(user.getDateOfBirth()));
            userRes.setGender(user.getGender());
            userRes.setAvatar(user.getAvatar());
            userRes.setIsOnline(user.getIsOnline());
            return userRes;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UserResponse> getUserNotIsManager() {
        try {
            List<User> users = userDao.searchUserNotIsManager();
            List<UserResponse> list = new ArrayList<>();
            for (User user : users) {
                UserResponse userRes = new UserResponse();
                userRes.setId(user.getId());
                userRes.setRoleId(user.getRole() == null ? null : user.getRole().getId());
                userRes.setFacultyId(user.getFaculty() == null ? null : user.getFaculty().getId());
                userRes.setCode(user.getCode() == null ? "" : user.getCode());
                userRes.setFullName(user.getFullName() == null ? "" : user.getFullName());
                userRes.setRoleName(user.getRole() == null ? "" : user.getRole().getName());
                userRes.setFacultyName(user.getFaculty() == null ? "" : user.getFaculty().getName());
                userRes.setEmail(user.getEmail() == null ? "" : user.getEmail());
                userRes.setAddress(user.getAddress() == null ? "" : user.getAddress());
                userRes.setPhoneNumber(user.getPhoneNumber() == null ? null : user.getPhoneNumber());
                userRes.setDateOfBirth(dateFormat(user.getDateOfBirth()));
                list.add(userRes);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean updatePassword(CreateAccount form, User user) {
        try {
            user.setPassword(bcryptEncoder.encode(form.getPassword()));
            user.setResetPasswordKey(null);
            user.setKeyCreatedAt(null);
            userDao.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean validateResetPasswordKey(String key) {
        try {
            User user = userDao.findExistedUserByPasswordKey(key);
            Date expireDate = DateUtils.addMinutes(user.getKeyCreatedAt(), minute);
            Boolean isExpired = new Date().after(expireDate);
            if (user == null || isExpired) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public FacebookUserDto getFacebookUser(String accessToken) {
        String path = "/me?fields={fields}&redirect={redirect}&access_token={access_token}";
        String fields = "email,first_name,last_name,birthday,id,picture.width(720).height(720)";
        final Map<String, String> variables = new HashMap<>();
        variables.put("fields", fields);
        variables.put("redirect", "false");
        variables.put("access_token", accessToken);
        return restTemplate.getForObject(facebook_graph_api_base + path, FacebookUserDto.class, variables);
    }

    @Override
    public LoginUser getLoginJwtTokenByFacebook(String accessToken) {
        FacebookUserDto facebookUserDto = getFacebookUser(accessToken);
        User user = userDao.findExistedUserByEmail(facebookUserDto.getEmail());
        if (user == null) {
            String password = RandomStringUtils.randomAscii(16);
            CreateAccount createAccount = new CreateAccount();
            createAccount.setEmail(facebookUserDto.getEmail());
            createAccount.setAvatar(facebookUserDto.getUrl());
            createAccount.setFullName(facebookUserDto.getFirstName() + " " + facebookUserDto.getLastName());
            createAccount.setPassword(password);
            try {
                createAccount.setDateOfBirth(DateUtils.parseDate(facebookUserDto.getBirthday(), "yyyy/MM/dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            createAccount.setAddress("12132132 asas");
            user = saveGuestRegister(createAccount);
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setId(user.getId());
        loginUser.setEmail(user.getEmail());
        return loginUser;
    }

    @Override
    public Boolean changePassword(ChangePasswordRequest request) {
        User user = userDao.findExistedUserByEmail(request.getEmail());
        if (bcryptEncoder.matches(request.getPassword(), user.getPassword()) == true) {
            user.setPassword(bcryptEncoder.encode(request.getNew_password()));
            userDao.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TotalCountResponse getTotalCountResponse() {
        try {
            User user = userDao.findExistedUserByEmail(getEmail());
            String role = getAuthority(user).toString();
            Long totalContributions;
            Long totalMagazines;
            Long totalStudents;
            switch (role) {
            case "[ROLE_R0002]":
                totalStudents = userDao.countExistedStudent();
                totalMagazines = magazineDao.countMagazine();
                totalContributions = contributionDao.countContributionByUserId(null);
                break;
            case "[ROLE_R0003]":
                totalStudents = userDao.countExistedStudent();
                totalMagazines = magazineDao.countMagazine();
                totalContributions = contributionDao.countContributionByUserId(null);
                break;
            case "[ROLE_R0004]":
                totalStudents = null;
                totalMagazines = magazineDao.countMagazine();
                totalContributions = contributionDao.countContributionByUserId(user.getId());
                break;
            case "[ROLE_R0005]":
                totalStudents = null;
                totalMagazines = magazineDao.countMagazine();
                totalContributions = contributionDao.countContributionByUserId(null);
                break;
            default:
                totalContributions = null;
                totalMagazines = null;
                totalStudents = null;
                break;
            }
            TotalCountResponse totalCountResponse = new TotalCountResponse();
            totalCountResponse.setTotalContributions(totalContributions);
            totalCountResponse.setTotalMagazines(totalMagazines);
            totalCountResponse.setTotalStudents(totalStudents);
            return totalCountResponse;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UserWithMostContributionResponse> getUserWithMostContribution(Long magazineId) {
        int limit = 5;
        List<Object> userWithMostContributionList = userDao.getTopUserWithMostContributions(magazineId, limit);
        List<UserWithMostContributionResponse> userWithMostContributionResponseList = new ArrayList<>();
        for (Object object : userWithMostContributionList) {
            UserWithMostContributionResponse userWithMostContributionResponse = new UserWithMostContributionResponse();
            Object[] user = (Object[]) object;
            if (((Object[]) user)[0] != null) {
                userWithMostContributionResponse.setCode((String) ((Object[]) user)[0]);
            }
            if (((Object[]) user)[1] != null) {
                userWithMostContributionResponse.setStudentId(Long.parseLong(((Object[]) user)[1].toString()));
            }
            if (((Object[]) user)[2] != null) {
                userWithMostContributionResponse.setStudentName((String) ((Object[]) user)[2]);
            }
            if (((Object[]) user)[3] != null) {
                userWithMostContributionResponse.setAvatar((String) ((Object[]) user)[3]);
            }
            if (((Object[]) user)[4] != null) {
                userWithMostContributionResponse.setFacultyName((String) ((Object[]) user)[4]);
            }
            if (((Object[]) user)[5] != null) {
                userWithMostContributionResponse.setFacultyId(Long.parseLong(((Object[]) user)[5].toString()));
            }
            if (((Object[]) user)[6] != null) {
                userWithMostContributionResponse.setTotalContribution(Long.parseLong(((Object[]) user)[6].toString()));
            }
            userWithMostContributionResponseList.add(userWithMostContributionResponse);
        }
        return userWithMostContributionResponseList;
    }

    @Override
    public Boolean updateOnlineStatus(int status, String session) {
        try {
            switch (status) {
            case ONLINE:
                User userEmail = userDao.findExistedUserByEmail(getEmail());
                userEmail.setIsOnline(status);
                userEmail.setCurrentSession(session);
                userDao.save(userEmail);
                return true;
            case OFFLINE:
                User userSession = userDao.findExistedUserByCurrenSession(session);
                userSession.setIsOnline(status);
                userSession.setCurrentSession(null);
                userDao.save(userSession);
                return true;
            default:
                break;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public LoginUser getGoogleUser(String accessToken) {
        try {
            String link = env.getProperty("google.link.get.user_info") + accessToken;
            String response = Request.Get(link).execute().returnContent().asString();
            ObjectMapper mapper = new ObjectMapper();
            GoogleUserDto googleUserDto = mapper.readValue(response, GoogleUserDto.class);
            if (!googleUserDto.getEmail().contains("@fpt")) {
                return null;
            }
            User user = userDao.findExistedUserByEmail(googleUserDto.getEmail());
            if (user == null) {
                String password = RandomStringUtils.randomAscii(16);
                CreateAccount createAccount = new CreateAccount();
                createAccount.setEmail(googleUserDto.getEmail());
                createAccount.setAvatar(googleUserDto.getPicture());
                createAccount.setFullName(googleUserDto.getName());
                createAccount.setPassword(password);
                try {
                    createAccount.setDateOfBirth(DateUtils.parseDate("2000/01/01", "yyyy/MM/dd"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                createAccount.setAddress("12132132 asas");
                user = saveGuestRegister(createAccount);
            }
            LoginUser loginUser = new LoginUser();
            loginUser.setId(user.getId());
            loginUser.setEmail(user.getEmail());
            return loginUser;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getLoginJwtTokenByGoogle(String code) {
        try {
            String link = env.getProperty("google.link.get.token");
            String response = Request.Post(link)
                    .bodyForm(Form.form().add("client_id", env.getProperty("google.app.id"))
                            .add("client_secret", env.getProperty("google.app.secret")).add("redirect_uri", "false")
                            .add("code", code).add("grant_type", "authorization_code").build())
                    .execute().returnContent().asString();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response).get("access_token");
            return node.textValue();
        } catch (Exception e) {
            return null;
        }
    }

}
