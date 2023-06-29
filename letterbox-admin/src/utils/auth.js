import Cookies from "js-cookie";

//设置Token
export function setToken(token) {
    Cookies.set("token", token);
}

//从当前Cookie中获取Token
export function getToken() {
    return Cookies.get("token");
}

//从当前Cookie中删除Token
export function delToken() {
    Cookies.remove("token");
}

//更新Token
export function refreshToken(token) {
    setToken(token);
}