package com.efrobot.weixin.util;

/**
 * 调用现金红包接口请求参数xml
 * 
 * @Description
 *
 * @author wurui. Email:wurui@ren001.com
 * @date 2016年1月15日 下午8:02:00
 */
public class RefundRequestXmlBean {

	private String appid;

	private String mch_id;

	private String nonce_str;

	private String sign;

	private String out_trade_no;

	private String out_refund_no;// 商户退款单号

	private Integer total_fee;// 商户退款单号

	private Integer refund_fee;// 商户退款单号

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public Integer getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(Integer refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public RefundRequestXmlBean(String appid, String mch_id, String nonce_str, String sign, String out_trade_no,
			String out_refund_no, Integer total_fee, Integer refund_fee) {
		super();
		this.nonce_str = nonce_str;
		this.appid = appid;
		this.mch_id = mch_id;
		this.out_trade_no = out_trade_no;
		this.out_refund_no = out_refund_no;
		this.total_fee = total_fee;
		this.refund_fee = refund_fee;
		this.sign = sign;
	}
}
