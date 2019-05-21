package com.xws.xysz.util;

import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Administrator on 2015/5/4.
 */
public class ImgCode {
    /**
     * 验证码图片的宽度。
     */
    private int width = 70;

    /**
     * 验证码图片的高度。
     */
    private int height = 30;

    /**
     * 验证码字符个数
     */
    private int codeCount = 5;

    /**
     * xx
     */
    private int xx = 0;

    /**
     * 干扰线数量
     */
    private int raltCount = 20;

    /**
     * 噪音点数量
     */
    private int pointCount = 30;

    /**
     * 字体高度
     */
    private int fontHeight;

    /**
     * codeY
     */
    private int codeY;

    /**
     * codeSequence
     */
    String[] codeSequence = {"3", "4", "5", "7", "8", "A", "a", "B", "b", "c", "C"
            , "D", "d", "E", "e", "F", "f", "G", "X", "Q", "v", "h", "H", "K", "k"
            , "N", "n", "R", "r", "S", "s", "T", "t","U","u","x","Y","y"};

    private Random random;

    /**
     * 初始化验证图片属性
     */
    public ImgCode(int width, int height, int codeCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        // 宽度
        String strWidth = width + "";
        // 高度
        String strHeight = height + "";
        // 字符个数
        String strCodeCount = codeCount + "";

        // 将配置的信息转换成数值
        try {
            if (strWidth != null && strWidth.length() != 0) {
                width = Integer.parseInt(strWidth);
            }
            if (strHeight != null && strHeight.length() != 0) {
                height = Integer.parseInt(strHeight);
            }
            if (strCodeCount != null && strCodeCount.length() != 0) {
                codeCount = Integer.parseInt(strCodeCount);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        xx = width / (codeCount);  //生成随机数的水平距离
        fontHeight = height - 4;      //生成随机数的数字高度

        // 创建一个随机数生成器类
        random = new Random();
    }

    /**
     * 生成验证码，并得到相应图片，验证码存入session中
     *
     * @param req
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public BufferedImage getImg(HttpServletRequest req)
            throws ServletException, IOException,FontFormatException {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D gd = buffImg.createGraphics();
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
//        Font font =  new Font("Fixedsys", Font.PLAIN, fontHeight - 8);

//        Font font = gd.getFont();
        Font font = Font.createFont(Font.TRUETYPE_FONT,new ClassPathResource("fonts/mvboli.ttf").getInputStream());

        font = font.deriveFont(Font.PLAIN,fontHeight-8);
        // 设置字体。
        gd.setFont(font);

        double radianPercent = 0D;

        // 随机产生干扰线，使图象中的认证码不易被其它程序探测到。
        for (int i = 0; i < raltCount; i++) {
            randomColor(gd, 70);
            String strRand = String.valueOf(codeSequence[random.nextInt(27)]);
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            radianPercent = Math.PI * (random.nextInt(90) / 180D);
            if (random.nextBoolean()) radianPercent = -radianPercent;
            gd.rotate(radianPercent, x, y);
            gd.drawString(strRand, x, y);
            gd.rotate(-radianPercent, x, y);
        }


        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();

        // 创建字体，字体的大小应该根据图片的高度来定。
        font = font.deriveFont(Font.BOLD,fontHeight);
        // 设置字体。
        gd.setFont(font);
        // 随机产生codeCount数字的验证码。
        for (int i = 1; i <= codeCount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(27)]);
            // 用随机产生的颜色将验证码绘制到图像中。
            randomColor(gd,150,150,150,255);
            radianPercent = Math.PI * (random.nextInt(30) / 180D);
            if (random.nextBoolean()) {
                radianPercent = -radianPercent;
            }
            int codeY = height - random.nextInt(height/4);
            int codeX = (i -1 ) * xx ;
            if(i == 1){
                codeX = xx /5;
            }else if(i == codeCount){
                codeX -= xx/5;
            }
            if(i < codeCount) {
                gd.rotate(radianPercent, codeX, codeY);
            }
            gd.drawString(strRand, codeX,  codeY);
            if(i < codeCount) {
                gd.rotate(-radianPercent, codeX, codeY);
            }
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute("imgCode", randomCode.toString());

        return buffImg;
    }

    /**
     * 将画笔设置为随机颜色
     *
     * @param gd
     */
    private void randomColor(Graphics2D gd, int alpha) {
        randomColor(gd,255,255,255,alpha);
    }

    private void randomColor(Graphics2D gd, int red,int green,int blue,int alpha) {
        //产生随机的颜色分量来构造颜色值
        red = random.nextInt(red);
        green = random.nextInt(green);
        blue = random.nextInt(blue);
        gd.setColor(new Color(red, green, blue, alpha));
    }

    private void randomColor(Graphics2D gd) {
        //产生随机的颜色分量来构造颜色值
        randomColor(gd, 255);
    }
}

