package com.efrobot.weixin.util;

import org.apache.log4j.Logger;

import com.efrobot.weixin.job.GetWXAccessJob;

/**
 * 水货众筹红包红包线程
 * 
 * @author wangxiangxiang
 * 
 */
public class SendRedPackegs implements Runnable {
	
	
	private static final Logger log = Logger.getLogger(SendRedPackegs.class);
	
	private static String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
//	private RedPacketsRecordService redPacketsdRecordService = (RedPacketsRecordService)BeanFactory.getBean("redPacketsRecordService");
	
	
	private String openid;		//微信用户唯一标识
	private String amount;		//红包金额	
	private String contentMsg;  //消息内容

	public SendRedPackegs(String openid, String amount,String contentMsg) {
		super();
		this.openid = openid;
		this.amount = amount;
		this.contentMsg = contentMsg;
	}

	@Override
	public void run() {
//		try {
		//正常查询，不带for update
//		log.info("【水货众筹红包】，开始执行SendTenRaisePublicRedPackegs的run方法");
//		log.info("SendRedPackegs==================>run()");
//		long start = System.currentTimeMillis();
//		//等待主线程事务提交，否则获取不到数据
//		long end = System.currentTimeMillis();
//		log.info("【水货众筹红包】，查询用户红包发放记录耗时："+(end-start)+"ms");
//				
//		start = System.currentTimeMillis();
//		RedPacketsRecord redPacketsdRecord=new RedPacketsRecord();
//		redPacketsdRecord.setOpenId(openid);
//		redPacketsdRecord.setStatus("0");
//		List<RedPacketsRecord> redList=redPacketsdRecordService.findRedPacketsdRecord(redPacketsdRecord);
//		if(redList.size()==0){
//			redPacketsdRecord.setId(UUID.randomUUID().toString());
//			redPacketsdRecord.setAmount(amount);
//			redPacketsdRecord.setCreateDate(new Date());
//			redPacketsdRecordService.insertRedPacketsdRecord(redPacketsdRecord);
//		}
//		Map<String, String> returnMsg = WeixinUtil.sendRedPack(openid, Integer.parseInt(amount), "进化者机器人小胖", "进化者机器人小胖", "小胖智能水壶恭祝您发大财", "众筹红包", "持续关注，更多惊喜等着你哦！");
//		end = System.currentTimeMillis();
//		log.info("【水货众筹红包】，发放水货众筹红包红包，调用红包接口耗时："+(end-start)+"ms");
//		log.info("【水货众筹红包】，发放水货众筹红包红包，调用红包接口耗时："+returnMsg.get("result_code"));
//		log.info("【水货众筹红包】，发放水货众筹红包红包，调用红包接口耗时："+returnMsg.get("return_msg"));
//		String msg=returnMsg.get("return_msg");
//		if("SUCCESS".equals(returnMsg.get("result_code"))){
//			start = System.currentTimeMillis();
//			if(msg.contains("帐号余额不足")){
//				sendCustomerMsg("抱歉，红包已经被抢完，参与<a href='https://z.jd.com/project/details/68513.html'>京东众筹</a>还有专属礼品送哦。<a href='http://u.eqxiu.com/s/1qP4sU9M?eqrcode=1&from=groupmessage&isappinstalled=0'>活动链接</a>");
//			}else{
//				redPacketsdRecord.setStatus("1");
//				redPacketsdRecord.setBillNo(returnMsg.get("mch_billno"));
//				redPacketsdRecord.setSendDate(new Date());
//				redPacketsdRecord.setResultCode(returnMsg.get("result_code"));
//				redPacketsdRecord.setReturnMsg(msg);
//				redPacketsdRecordService.updateRedPacketsdRecord(redPacketsdRecord);
//				sendCustomerMsg(contentMsg);
//			}
//			end = System.currentTimeMillis();
//		}else{
//			if(msg.contains("请求已受理，请稍后使用原单号查询发放结果")){
//				redPacketsdRecord.setStatus("1");
//				redPacketsdRecord.setBillNo(returnMsg.get("mch_billno"));
//				redPacketsdRecord.setSendDate(new Date());
//				redPacketsdRecord.setResultCode(returnMsg.get("result_code"));
//				redPacketsdRecord.setReturnMsg(msg);
//				redPacketsdRecordService.updateRedPacketsdRecord(redPacketsdRecord);
//			}else{
//				redPacketsdRecord.setStatus("0");
//				redPacketsdRecord.setBillNo(returnMsg.get("mch_billno"));
//				redPacketsdRecord.setSendDate(new Date());
//				redPacketsdRecord.setResultCode(returnMsg.get("result_code"));
//				redPacketsdRecord.setReturnMsg(msg);
//				redPacketsdRecordService.updateRedPacketsdRecord(redPacketsdRecord);
//				
//				start = System.currentTimeMillis();
//				if(msg.contains("余额不足")){
//					sendCustomerMsg("抱歉，红包已经被抢完，参与<a href='https://z.jd.com/project/details/68513.html'>京东众筹</a>还有专属礼品送哦。<a href='http://u.eqxiu.com/s/1qP4sU9M?eqrcode=1&from=groupmessage&isappinstalled=0'>活动链接</a>");
//				}else if(msg.contains("已被微信拦截")){
//					sendCustomerMsg("据微信官方提示，您的账号存在异常，无法领取红包，请您在手机端登陆活跃的微信账号参加活动，或咨询微信客服。");
//				}else{						
//					sendCustomerMsg("红包发送失败，请稍后再试");
//				}
//				end = System.currentTimeMillis();
//				log.info("【水货众筹红包】，红包发送失败，发送客服消息耗时："+(end-start)+"ms");
//			}				
//		}			
//		end = System.currentTimeMillis();
//		log.info("执行SendTenRaisePublicRedPackegs的run方法耗时："+(end-start)+"ms");
//		} catch (Exception e) {
//			sendCustomerMsg("正在领取红包");
//			e.printStackTrace();
//			log.error("发放水货众筹红包出错啦！！",e);
//		}
	}
	

	
	public String sendCustomerMsg(String content){
		//发送客服消息
		String postParam = "{\"touser\":\""+openid+"\",\"msgtype\":\"text\",\"text\":{\"content\": \""+content+"\"}}";
		return WeixinUtil.customerService(postParam,GetWXAccessJob.getAccessToken());
	}
	

}
