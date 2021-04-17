package com.example.server.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.server.config.TokenProvider;
import com.example.server.dto.AuthToken;
import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.model.request.CreateAccount;
import com.example.server.model.request.LoginUser;
import com.example.server.service.UserService;
import com.example.server.util.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.example.server.constant.Constant.*;
import static com.example.server.util.ResponseUtils.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
public class NonAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseUtils responseUtils;

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    private ResponseEntity<?> getResponseEntity(Object data, String code, String mess, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("code", code);
        response.put("messenger", mess);
        return new ResponseEntity<>(response, status);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser, HttpServletRequest request)
            throws AuthenticationException {
        try {
            User user = userService.findOne(loginUser.getEmail());
            if (user.getIs_deleted() == 1) {
                return getResponseEntity("NULL", "-1", "Account is deleted, login failed", HttpStatus.BAD_REQUEST);
            }
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
            User userLogin = userService.findOne(loginUser.getEmail());
            if (userLogin == null) {
                return getResponseEntity("NULL", "-1", "Login failed", HttpStatus.BAD_REQUEST);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            CLIENTURL = getSiteURL(request);
            return getResponseEntity(new AuthToken(token, userLogin.getId()), "1", "Login success", HttpStatus.OK);
        } catch (Exception e) {
            return getResponseEntity("NULL", "-1", "Login failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<?> saveUser(CreateAccount user, @RequestPart("file") MultipartFile file,
            HttpServletRequest httpServletRequest) {
        try {
            HashMap<String, Object> validateResult = responseUtils.validateCreateAccountRequest(user, file, 0);
            Object validateRes = validateResult.get("result");
            if (Integer.parseInt(validateRes.toString()) == -1) {
                return responseUtils.getActionResponseEntity("NULL", FAILURE, "Create user failed", validateResult,
                        HttpStatus.BAD_REQUEST);
            }
            User regisUser = userService.saveGuestRegister(user);
            if (regisUser == null) {
                return getResponseEntity("NULL", "1", "Register failed", HttpStatus.BAD_REQUEST);
            }
            return getResponseEntity("NULL", "1", "Register success", HttpStatus.OK);
        } catch (Exception e) {
            return getResponseEntity("NULL", "-1", "Register failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/facebook/login")
    public ResponseEntity<?> loginByFacebook(@RequestBody LoginUser loginUser) throws AuthenticationException {
        try {
            if (loginUser.getAccessToken() == null) {
                return getResponseEntity("NULL", "-1", "Login failed", HttpStatus.BAD_REQUEST);
            }
            LoginUser user = userService.getLoginJwtTokenByFacebook(loginUser.getAccessToken());
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            final Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            return getResponseEntity(new AuthToken(token, user.getId()), "1", "Login success", HttpStatus.OK);
        } catch (Exception e) {
            return getResponseEntity("NULL", "-1", "Login failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/google/login")
    public ResponseEntity<?> loginByGoogle(@RequestBody LoginUser loginUser) throws AuthenticationException {
        try {
            if (loginUser.getAccessToken() == null) {
                return getResponseEntity("NULL", "-1", "Login failed", HttpStatus.BAD_REQUEST);
            }
            // String accessToken =
            // userService.getLoginJwtTokenByGoogle(loginUser.getAccessToken());
            LoginUser user = userService.getGoogleUser(loginUser.getAccessToken());
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            final Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            return getResponseEntity(new AuthToken(token, user.getId()), "1", "Login success", HttpStatus.OK);
        } catch (Exception e) {
            return getResponseEntity("NULL", "-1", "Login failed", HttpStatus.BAD_REQUEST);
        }
    }

}
