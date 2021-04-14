<<<<<<< HEAD
let BaseUrl= "http://10.23.253.0:8080";
=======
let BaseUrl= "http://localhost:8080";
>>>>>>> b2275b1a598184fb6cc84c0affc70fd61295efd7
export  const UrlConstants = Object.freeze({
    BaseUrl: BaseUrl,
    GoogleLogin: BaseUrl + "/google/login",
    Login: BaseUrl + "/login",
    Logout: BaseUrl + "/users/logout",
    User: BaseUrl + "/users",
    Role: BaseUrl + "/roles",
    Faculty: BaseUrl + "/faculties",
    Contribution: BaseUrl + "/contributions",
    Comment:  BaseUrl + "/comments",
    Magazine:  BaseUrl + "/magazines",
    AvatarSource: BaseUrl + "/file/users/",
    SendMail: BaseUrl + "/mail/forgotpassword?",
    MailSubmit: BaseUrl + "/mail/contribution?id=",
    Chat: BaseUrl+"/chat"
});