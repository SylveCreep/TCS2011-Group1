package com.example.server.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import com.example.server.constant.Constant;
import com.example.server.dao.FacultyDao;
import com.example.server.dao.RoleDao;
import com.example.server.dao.UserDao;
import com.example.server.entity.Faculty;
import com.example.server.entity.Role;
import com.example.server.entity.User;
import com.example.server.model.request.CreateAccount;
import com.example.server.model.request.CreateRole;
import com.example.server.model.request.FacultyRequest;
import com.example.server.model.request.PagingRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.example.server.constant.Constant.*;

@Service
public class ResponseUtils {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private FacultyDao facultyDao;
    
    //Get sort object for sort
    public Sort getSortObj(PagingRequest pagingRequest){
        Sort sort;
        switch (pagingRequest.sort){
            case "desc":
                sort = Sort.by(Sort.Direction.DESC,pagingRequest.getColumn());
                break;
            default:
                sort = Sort.by(Sort.Direction.ASC,pagingRequest.getColumn());
        }
        return sort;
    }

    //Reponse format
    public ResponseEntity<?> getResponseEntity(Object data, int code, String mess, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("data",data);
        response.put("code",code);
        response.put("message",mess);
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<?> getResponseEntity(Object data, int code, String mess, Object total,  HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("data",data);
        response.put("code",code);
        response.put("lastPage",total);
        response.put("message",mess);
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<?> getResponseEntity(Object data, int code, String mess, Object total, Object validate,  HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("data",data);
        response.put("code",code);
        response.put("lastPage",total);
        response.put("message",mess);
        response.put("validate",validate);
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<?> getActionResponseEntity(Object data, int code, String mess, Object validate,  HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("data",data);
        response.put("code",code);
        response.put("message",mess);
        response.put("validate",validate);
        return new ResponseEntity<>(response, status);
    }

    public static String dateFormat(Date date){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public HashMap<String, Object> validateCreateAccountRequest(CreateAccount createAccount, MultipartFile file, int type){
        HashMap<String, Object> form = new HashMap<>();
        HashMap<String, Object> inputForm = new HashMap<>();
        form.put("input", inputForm);

        String valUpdateEmail = validateEmailUpdateInput(createAccount.getEmail(), createAccount.getId());
        String valEmail = validateEmailInput(createAccount.getEmail());
        String valPass = validatePasswordInput(createAccount.getPassword());
        String valName = validateNameInput(createAccount.getFullName());
        String valAddr = validateAddressInput(createAccount.getAddress());
        String valDob = validateBirthDayInput(createAccount.getDateOfBirth());
        String valRole = validateRoleInput(createAccount.getRoleId());
        String valFacl = validateFacultyInput(createAccount.getFacultyId());
        String valPhone = validatePhoneInput(createAccount.getPhoneNumber());
        String valGen = validateGenderInput(createAccount.getGender());
        String valId = validateUserInput(createAccount.getId());
        String valAvatar = validateImageFile(file);

        // Type 0 for create account request
        // Type 1 for update account request
        switch (type) {
            case 0:
                if(!valEmail.equals("Valid")){
                    inputForm.put("email", valEmail);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }
                if(!valPass.equals("Valid")){
                    inputForm.put("password", valPass);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }
                if(!valName.equals("Valid")){
                    inputForm.put("fullName", valName);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }
                if(!valAddr.equals("Valid")){
                    inputForm.put("address", valAddr);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }
                if(!valDob.equals("Valid")){
                    inputForm.put("dateOfBirth", valDob);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }
                if(!valRole.equals("Valid")){
                    inputForm.put("roleId", valRole);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }

                if(!valFacl.equals("Valid")){
                    inputForm.put("facultyId", valFacl);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }

                if(!valPhone.equals("Valid")){
                    inputForm.put("phoneNumber", valPhone);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }

                if(!valGen.equals("Valid")){
                    inputForm.put("gender", valGen);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }
                if(!valAvatar.equals("Valid")){
                    inputForm.put("avatar", valAvatar);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }

                if(!form.containsKey("result")){
                    form.put("result", 0);
                }
                break;
            case 1:
                if(!valId.equals("Valid")){
                    inputForm.put("userId", valEmail);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }

                // if(!valUpdateEmail.equals("Valid")){
                //     inputForm.put("email", valUpdateEmail);
                //     if(!form.containsKey("result")){
                //         form.put("result", -1);
                //     }
                // }
                // if(!valPass.equals("Valid")){
                //     inputForm.put("password", valPass);
                //     if(!form.containsKey("result")){
                //         form.put("result", -1);
                //     }
                // }
                if(!valName.equals("Valid")){
                    inputForm.put("fullName", valName);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }
                if(!valAddr.equals("Valid")){
                    inputForm.put("address", valAddr);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }
                if(!valDob.equals("Valid")){
                    inputForm.put("dateOfBirth", valDob);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }
                if(!valRole.equals("Valid")){
                    inputForm.put("roleId", valRole);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }

                if(!valFacl.equals("Valid")){
                    inputForm.put("facultyId", valFacl);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }

                if(!valPhone.equals("Valid")){
                    inputForm.put("phoneNumber", valPhone);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }

                if(!valGen.equals("Valid")){
                    inputForm.put("gender", valGen);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }
                if(!valAvatar.equals("Valid")){
                    inputForm.put("avatar", valAvatar);
                    if(!form.containsKey("result")){
                        form.put("result", -1);
                    }
                }

                if(!form.containsKey("result")){
                    form.put("result", 0);
                }
            default:
                break;
        }

        return form;
    }

    public String validateEmailInput(String email){
        if(email == null){
            return "Invalid";
        }
        User user = userDao.findByEmail(email);
        if(user != null){
            return  "Email existed";
        } else {
            return "Valid";
        }
    }

    public String validateEmailUpdateInput(String email, Long id){
        if(email == null || id == null){
            return "Invalid";
        }
        Optional<User> user = userDao.findById(id);
        User userE = userDao.findByEmail(email);
        if(user.get() != null && userE != null){
            if(user.get().getEmail().equals(email)){
                return  "Email existed";
            } else {
                return "Valid";
            }
        } else {
            return "Valid";
        }
    }

    public String validateNameInput(String fullName){
        if(fullName == null){
            return "Invalid";
        }
        if(!NameValidation.containSpecialCharacter(fullName) && !NameValidation.containNumber(fullName)){
            return "Valid";
        } else {
            return "Contain invalid character";
        }
    }

    public String validatePasswordInput(String password){
        if(password == null){
            return "Invalid";
        }
        if(password.length() >= 6){
            return "Valid";
        } else {
            return "Invalid length";
        }
    }

    public String validateAddressInput(String address){
        if(address == null){
            return "Invalid";
        }
        if(!NameValidation.containSpecialCharacter(address)){
            return "Valid";
        } else {
            return "Contain invalid character";
        }
    }

    public String validateBirthDayInput(Date dateOfBirth){
        if(dateOfBirth == null){
            return "Invalid";
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        if((Integer.parseInt(dateFormat.format(currentDate).substring(0, 4)) - Integer.parseInt(dateFormat.format(dateOfBirth).substring(0, 4))) > 18){
            return "Valid";
        } else {
            return "Must over 18 years old";
        }
    }

    public String validateRoleInput(Long roleId){
        if(roleId == null){
            return "Valid";
        }
        Role role = roleDao.findRoleById(roleId);
        if(role != null){
            if(role.getIs_deleted() != DELETED){
                return "Valid";
            } else {
                return "Role is deleted";
            }
        } else {
            return "Role not existed";
        }
    }

    public String validateUserInput(Long userId){
        if(userId == null){
            return "Must have user Id";
        }
        Optional<User> user = userDao.findById(userId);
        if(!user.isEmpty()){
            if(user.get().getIs_deleted() != DELETED){
                return "Valid";
            } else {
                return "User is deleted";
            }
        } else {
            return "User not existed";
        }
    }

    public String validateFacultyInput(Long facultyId){
        if(facultyId == null){
            return "Valid";
        }
        Faculty faculty = facultyDao.getOne(facultyId);
        if(faculty != null){
            if(faculty.getIs_deleted() != DELETED){
                return "Valid";
            } else {
                return "Faculty is deleted";
            }
        } else {
            return "Faculty not existed";
        }
    }

    public String validatePhoneInput(Long phoneNumber){
        if(phoneNumber == null){
            return "Invalid";
        }
        if(phoneNumber.toString().length() < 15 && phoneNumber.toString().length() >= 9){
            return "Valid";
        } else {
            return "Invalid length";
        }
    }

    public String validateGenderInput(Integer gender){
        if(gender == null){
            return "Invalid";
        }
        if(gender != MALE && gender != FEMALE){
            return "Invalid";
        } else {
            return "Valid";
        }
    }

    public String validateImageFile(MultipartFile file){
        if(file == null){
            return "Invalid";
        }
        if(!file.getContentType().startsWith("image/")){
            return file.getContentType();
        }
        return "Valid";
    }


    //Validate Role
    public HashMap<String, Object> validateRoleRequest(CreateRole createRole, int type){
        HashMap<String, Object> form = new HashMap<>();
        HashMap<String, Object> inputForm = new HashMap<>();
        form.put("input", inputForm);

        //String valUpdateNameRole = validateNameRoleUpdateInput(createRole.getName(), createRole.getId());
        //String valUpdateCodeRole = validateCodeRoleUpdateInput(createRole.getCode(), createRole.getId());
        String valNameRole  = validateNameRoleInput(createRole.getName());
        String valCodeRole = validateCodeRoleInput(createRole.getCode());
        String valId = validateIdInput(createRole.getId());

        switch(type){
            case 0:
               if (!valNameRole.equals("Valid")){
                   inputForm.put("name", valNameRole);
                   if (!form.containsKey("result")){
                       form.put("result", -1);
                   }
               }
            //    if (!valCodeRole.equals("Valid")){
            //        inputForm.put("code", valCodeRole);
            //        if (!form.containsKey("result")){
            //            form.put("result", -1);
            //        }
            //    }
               if (!form.containsKey("result")){
                   form.put("result", 0);
               }
               break;
            case 1:
               if (!valId.equals("Valid")){
                   inputForm.put("id", valId);
                   if (!form.containsKey("result")){
                       form.put("result", -1);
                   }
               }
               if (!valNameRole.equals("Valid")){
                   inputForm.put("name", valNameRole);
                   if (!form.containsKey("result")){
                       form.put("result", -1);
                   }
               }
               if (!valCodeRole.equals("Valid")){
                   inputForm.put("code", valCodeRole);
                   if (!form.containsKey("result")){
                       form.put("result", -1);
                   }
               }
               if (!form.containsKey("result")){
                   form.put("result", 0);
               }
               break;
            default:
               break;
        }

        return form;
    }

    public String validateNameRoleInput(String name){
        if (name == null){
            return "Invalid";
        }
        if (NameValidation.containSpecialCharacter(name)){
            return "Invalid";
        }
        Role role = roleDao.findRoleByName(name);
            if (role != null){
                return "Name existed";
            } else {
                return "Valid";
            } 
    }

    public String validateCodeRoleInput(String code){
        if (code == null){
            return "Invalid";
        }
        if  (NameValidation.containSpecialCharacter(code)){
            return "Invalid";
        }
        Role role = roleDao.findRoleByCode(code);
            if (role != null){
                return "Code existed";
            }else {
                return "Valid";
            }
    }

    public String validateIdInput(Long id){
        if (id == null){
            return "Must have role id";
        }
        Optional<Role> role = roleDao.findById(id);
        if (!role.isEmpty()){
            if (role.get().getIs_deleted() != Constant.DELETED){
                return "Valid";
            }else{
                return "Role is deleted";
            }
        } else{
            return "Role not existed";
        }
    }

    //Validate faculty
    public HashMap<String, Object> validateFacultyRequest(FacultyRequest facultyRequest, int type){
        HashMap<String, Object> form = new HashMap<>();
        HashMap<String, Object> inputForm = new HashMap<>();
        form.put("input", inputForm);
        switch (type) {
            case 0:
                
                break;
            case 1:
                break;
        
            default:
                break;
        }
        return form;
    }

}
