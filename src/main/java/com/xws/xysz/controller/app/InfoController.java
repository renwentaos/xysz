package com.xws.xysz.controller.app;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 个人资料
 * @Author rwt
 * @Date 2018/11/19 14:26
 **/
@Controller("appInfo")
@RequestMapping("/app/member")
public class InfoController {


//    @Autowired
//    MemberService memberService;
//    @Autowired
//    MemberBankCardService memberBankCardService;
//    @Autowired
//    VoucherRecordService voucherRecordService;
//    @Autowired
//    RepaymentPlanService repaymentPlanService;
//
//
//    /**
//     * 实名认证
//     *
//     * @param memberId      会员id
//     * @param name          真实姓名
//     * @param cardNumber    证件号码
//     * @param certificateX  图片1
//     * @param certificateY  2
//     * @param model
//     */
//    @RequestMapping(value = "/verificat/name", method = RequestMethod.POST)
//    public void verificatName(String memberId, String name, String cardNumber,
//                              String certificateX, String certificateY, Model model) {
//        APPResult appResult = new APPResult();
//        try {
//            Member member = memberService.verificatName(Integer.valueOf(memberId), name, cardNumber, certificateX, certificateY);
//            appResult.setResponseCode(APICode.成功.getCode());
//            appResult.setResult(member);
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 银行卡绑定
//     *
//     * @param memberId       会员id
//     * @param bankName       银行名称
//     * @param bankCardNumber 银行卡号
//     * @param cardPic1       卡正面照片
//     * @param cardPic2       卡反面照片
//     * @param memberName     银行卡户主
//     * @param model
//     */
//    @RequestMapping(value = "/banding/card", method = RequestMethod.POST)
//    public void bandingCard(String memberId, String bankName, String bankCardNumber, String cardPic1, String cardPic2, String memberName,String bankTel, Model model) {
//        APPResult appResult = new APPResult();
//        try {
//            Member member = memberService.bandingCard(memberId, bankName, bankCardNumber, cardPic1, cardPic2, memberName,bankTel);
//            appResult.setResponseCode(APICode.成功.getCode());
//            appResult.setResult(member);
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 删除银行卡
//     *
//     * @param memberId
//     * @param cardId
//     * @param model
//     */
//    @RequestMapping(value = "/delete/card", method = RequestMethod.POST)
//    public void deleteCard(String memberId, String cardId, Model model) {
//        APPResult appResult = new APPResult();
//        try {
//            memberBankCardService.deleteCard(memberId, cardId);
//            appResult.setResponseCode(APICode.成功.getCode());
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 银行卡列表
//     * @param memberId
//     * @param model
//     */
//    @RequestMapping(value = "/card/list",method = RequestMethod.POST)
//    public void bankCardList(String memberId, String projectId, Model model){
//        APPResult appResult = new APPResult();
//        try{
//            List<MemberBankCard> list =  memberBankCardService.getbankCardList(memberId,projectId);
//            appResult.setResponseCode(APICode.成功.getCode());
//            appResult.setResult(list);
//        }catch (WarnException e){
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        }finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//
//    /**
//     * 基本信息完善  /修改
//     *
//     * @param memberId   会员id
//     * @param email      邮箱
//     * @param wechat     微信
//     * @param qq         QQ
//     * @param address    现居地址
//     * @param firmName   公司名称
//     * @param firmAddess 公司地址
//
//     */
//    @RequestMapping(value = "/complete/info", method = RequestMethod.POST)
//    public void completeInfo(String memberId, String email, String wechat, String qq, String address, String firmName,
//                             String firmAddess, String workYear, String industry, String positions,
//                             String typeOne, String remarkOne, String telOne, String nameOne,
//                             String typeTwo, String remarkTwo, String telTwo, String nameTwo, Model model) {
//        APPResult appResult = new APPResult();
//        try {
//            Member member = memberService.completeInfo(memberId, email, wechat, qq, address, firmName, firmAddess,workYear,industry,positions
//            ,typeOne,remarkOne,telOne,nameOne,typeTwo,remarkTwo,telTwo,nameTwo);
//            appResult.setResponseCode(APICode.成功.getCode());
//            appResult.setResult(member);
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 芝麻分认证
//     * @param memberId
//     * @param sesame
//     * @param model
//     */
//    @RequestMapping(value = "/sesame",method = RequestMethod.POST)
//    public void sesame(String memberId, String sesame, Model model){
//        APPResult appResult = new APPResult();
//        try {
//            Member member = memberService.addSesame(memberId, sesame);
//            appResult.setResponseCode(APICode.成功.getCode());
//            appResult.setResult(member);
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 导入通讯录
//     * @param memberId
//     * @param contactsList
//     * @param model
//     */
//    @RequestMapping(value = "/contacts",method = RequestMethod.POST)
//    public void addContacts(String memberId, String contactsList, Model model){
//        APPResult appResult = new APPResult();
//        try {
//             memberService.addContacts(memberId, contactsList);
//            appResult.setResponseCode(APICode.成功.getCode());
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//
//    /**
//     * 我的代金券
//     *
//     * @param memberId
//     * @param model
//     */
//    @RequestMapping(value = "/voucher/list/{pageNo}-{pageSize}", method = RequestMethod.POST)
//    public void voucherList(String memberId, @PathVariable String pageNo, @PathVariable String pageSize, String state, Model model) {
//        APPResult appResult = new APPResult();
//        Map<String,Object> map = new HashMap<>();
//        Date date = new Date();
//        PageInfo pageInfo = voucherRecordService.selectVoucherList(memberId, pageNo, pageSize,state,date);
//        BigDecimal balance = voucherRecordService.getBalance(memberId,date);
//        map.put("pageInfo",pageInfo);
//        map.put("balance",balance);
//        appResult.setResult(map);
//        appResult.setResponseCode(APICode.成功.getCode());
//        model.addAttribute(appResult);
//    }
//
//
//    /**
//     * APP获取七牛上传图片所需数据
//     */
//    @RequestMapping(value = "/qn/token",method = RequestMethod.POST)
//    public void getQNToken(Model model){
//        APPResult appResult = new APPResult();
//        appResult.setResponseCode(APICode.成功.getCode());
//        appResult.setResult(QNUtil.getUpToken());
//        model.addAttribute(appResult);
//    }
//
//    /**
//     * 修改密码
//     * @param memberId
//     * @param oldPwd
//     * @param password
//     * @param password1
//     * @param model
//     */
//    @RequestMapping(value = "/update/pwd",method = RequestMethod.POST)
//    public void updatePwd(String memberId, String oldPwd, String password,String password1, Model model) {
//        APPResult appResult = new APPResult();
//        try {
//            memberService.updatePwd(memberId,  PwsUtil.dncrypt(oldPwd), PwsUtil.dncrypt(password),PwsUtil.dncrypt(password1));
////           memberService.updatePwd(memberId,  oldPwd,password,password1);
//            appResult.setResponseCode(APICode.成功.getCode());
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 获取最新一期代还款信息
//     */
//
//    @RequestMapping(value = "/new/repay/plan",method = RequestMethod.POST)
//    public void getNewRepayPlan(String memberId, Model model){
//        APPResult appResult = new APPResult();
//        RepaymentPlan repaymentPlan = repaymentPlanService.getNewRepayPlan(memberId);
//        appResult.setResponseCode(APICode.成功.getCode());
//        appResult.setResult(repaymentPlan);
//        model.addAttribute(appResult);
//    }
//
//    /**
//     * 我的还款列表
//     */
//
//    @RequestMapping(value = "/repay/{pageNo}-{pageSize}",method = RequestMethod.POST)
//    public void getRepay(String memberId, @PathVariable String pageNo, @PathVariable String pageSize, Model model){
//        APPResult appResult = new APPResult();
//        PageInfo pageInfo = repaymentPlanService.getRepay(memberId,pageNo,pageSize);
//        appResult.setResponseCode(APICode.成功.getCode());
//        appResult.setResult(pageInfo);
//        model.addAttribute(appResult);
//    }
//
//    /**
//     * 上传手持身份证照片
//     * @param memberId
//     * @param pic
//     * @param model
//     */
//    @RequestMapping(value = "/update/pic",method = RequestMethod.POST)
//    public void updateHandPic(String memberId, String pic, Model model){
//        APPResult appResult = new APPResult();
//        try {
//            Member member = memberService.updatePicByMemberId(Integer.valueOf(memberId),pic);
//            appResult.setResponseCode(APICode.成功.getCode());
//            appResult.setResult(member);
//        }catch (WarnException e){
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        }finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 个人资料完善 附加信息
//     * @param memberId
//     * @param housePic 房产证图片
//     * @param carPic 行车证图片
//     * @param parkPic 车位合同图片
//     * @param otherPic 其他证件图片
//     * @param model
//     */
//    @RequestMapping(value = "/append/info", method = RequestMethod.POST)
//    public void appendInfo(String memberId, String housePic, String carPic, String parkPic, String otherPic, Model model) {
//        APPResult appResult = new APPResult();
//        try {
//            Member member = memberService.appendInfo(Integer.valueOf(memberId),housePic,carPic,parkPic,otherPic);
//            appResult.setResponseCode(APICode.成功.getCode());
//            appResult.setResult(member);
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 退出登录
//     * @param memberId
//     * @param model
//     */
//    @RequestMapping(value = "/logout",method = RequestMethod.POST)
//    public void  logout(String memberId, Model model){
//        APPResult appResult = new APPResult();
//        memberService.updatePushIdByMemberId(Integer.valueOf(memberId));
//        appResult.setResponseCode(APICode.成功.getCode());
//        model.addAttribute(appResult);
//    }
}
