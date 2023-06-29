import JSEncrypt from "jsencrypt"

// http://web.chacuo.net/netrsakeypair 设置加密公钥和私钥

const publicKey = ""

const privateKey = ""

//加密
export function encrypt(txt) {
    const encryptor = new JSEncrypt()
    encryptor.setPublicKey(publicKey) //设置公钥
    return encryptor.encrypt(txt) //加密
}

//解密
export function decrypt(txt) {
    const encryptor = new JSEncrypt()
    encryptor.setPrivateKey(privateKey) //设置私钥
    return encryptor.decrypt(txt) //解密
}