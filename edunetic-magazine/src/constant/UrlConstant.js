let BaseUrl= "http://localhost:8080";
export  const UrlConstants = Object.freeze({
    BaseUrl: BaseUrl,
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
    MailSubmit: BaseUrl + "/mail/contribution?",
    Chat: BaseUrl+"/chat"
});