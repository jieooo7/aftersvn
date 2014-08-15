/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package cn.icnt.dinners.alipay;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088212864450796";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "darren@icnt.cn";

	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANqvP8TSVykCrcysZ7+Du/1XSDuCxqKKLyqnlKbiU/VCdl5Y8HMzcQgMsh/tecjAnVy/dZxzu9WSrmMewMKeoCocVBdwzYJweLVqu1vpQu55JiKPd/TzYHxiEqGriaeGefxLTMQSAwyXjpqcKeYo7mvrdo3AV2cPYrzVeZ60MAJ9AgMBAAECgYEAzFU7zHiNoHA0XS4qsrQwj4NiptiHKZDciYqUR2rq8Bvt5jJMWhgS4WabeWG3jXEMmID9L/aj5+dqsoslqbP2Cii0sITrwYUM4Io+T1q5qIounAXa0yUA+eIMapCqWJ8vVADBoYUP7SLZefVtgNJ5r2MCIEn7CC74XnLjB7reFgECQQDxZqFJ5fbDoeMJKcW7YpqQUfs1+XSCNcKZwxbfphGg/VIxfdyUFODOToY/EwUvya6ixvaZDpejEGcIu21u1SAhAkEA5+jsuOeed6+eS5EtumN7pF8jqzQeBBZptDe+cl8OimbDBDrR296cNuuUNZgDzj2Y9d8NC2C/U/jIKns50yKG3QJBAI8SXqFntDr6ZY72vQR0Slsi25p/wHez2SQ8pf/jcytRODew+tyhCyP7EkGryjUqeWBsP5czONgiTo60VwWLN0ECQCu5GohyAsqF7c5JfNmbUABZf5wTo2UZ5Kv5pophqZgmb7YocNCHU+R23eaM1orXyjLw7sCqaOgoC3UW7nwEEnECQD/iRzjxFJRxUZYQy4FLqSEDDTU1s/567AprI2Mp6UuqK27wCrls469AFjbva0jlfZohLWQiCvTukhbpwZ2M3mQ=";

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	
//	Intent intent = new Intent("com.alipay.android.app.IAlixPay"); //快捷插件
//	mActivity.getApplicationContext().bindService(intent, mAlixPayConnection, Context.BIND_AUTO_CREATE);
//	或者
//	Intent intent = new Intent("com.eg.android.AlipayGphone.IAlixPay");//钱包
//	mActivity.getApplicationContext().bindService(intent, mAlixPayConnection, Context.BIND_AUTO_CREATE);
}
