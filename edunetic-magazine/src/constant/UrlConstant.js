let BaseUrl= "https://1b4e29b70259.ngrok.io";
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