package ltd.newbee.mall.util;




import org.jsoup.internal.StringUtil;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    /**
     * 合并两个图片
     * @param img1Path 图片1地址
     * @param img2Path 图片2地址
     * @param x 图片2左上点在X轴起始坐标
     * @param y 图片2左上点在y轴起始坐标
     * @param destPath 合并两个图片生成新的图片地址
     * @param formatName 图片格式  bmp|gif|jpg|jpeg|png
     * @param maxBorder ture:取两图最大边框;false:以图片1尺寸和坐标为准
     * @return
     * @throws IOException
     */
    public static boolean mergeImages(String img1Path, String img2Path,
                                      int x, int y, String destPath, String formatName, boolean maxBorder) throws IOException {
        boolean isSuccess = false;
        if (!StringUtils.isEmpty(img1Path) && !StringUtils.isEmpty(img2Path)) {
            BufferedImage img1 = ImageIO.read(new File(img1Path));//读取图片1
            BufferedImage img2 = ImageIO.read(new File(img2Path));//读取图片2
            if (!maxBorder) {//以图片1尺寸和坐标为准
                isSuccess = drawNewImageInImage1(img1, img2, x, y, destPath, formatName);
            } else {//取两图最大边框
                if (!needReptain(img1, img2, x, y)) {
                    isSuccess = drawNewImageInImage1(img1, img2, x, y, destPath, formatName);
                } else {
                    int w1 = img1.getWidth();
                    int w2 = img2.getWidth();
                    int wx1 = w1 - x;

                    int h1 = img1.getHeight();
                    int h2 = img2.getHeight();
                    int hy1 = h1 - y;
                    //创建背景图片
                    BufferedImage blankImage = new BufferedImage(wx1 >= w2 ? (x >= 0 ? w1 : w1 - x) : (x >= 0 ? w2 + x : w2),
                            hy1 > h2 ? (y >= 0 ? h1 : h1 - y) : (y >= 0 ? h2 + y : h2), BufferedImage.TYPE_INT_RGB);
                    //画图片1、图片2
                    Graphics blankImgGraphics = null;
                    try {
                        blankImgGraphics = blankImage.getGraphics();
                        blankImgGraphics.drawImage(img1, x < 0 ? -1*x : 0, y < 0 ? -1*y : 0, img1.getWidth(), img1.getHeight(), null);
                        blankImgGraphics.drawImage(img2, x < 0 ? 0 : x, y < 0 ? 0 : y, img2.getWidth(), img2.getHeight(), null);
                        isSuccess = ImageIO.write(blankImage, formatName, new File(destPath));
                    } finally {
                        if (blankImgGraphics != null) {
                            blankImgGraphics.dispose();
                        }
                    }
                }
            }
        }
        return isSuccess;
    }

    /**
     * 在图片1中画图片2
     * @param x X坐标位置
     * @param y Y坐标位置
     * @param destPath 新图片存储位置
     * @param formatName 图片格式
     * @param img1 图片1
     * @param img2 图片2
     * @return
     * @throws IOException
     */
    private static boolean drawNewImageInImage1(BufferedImage img1, BufferedImage img2,
                                                int x, int y, String destPath, String formatName) throws IOException {
        boolean isSuccess;
        Graphics img1Graphics = null;
        try {
            img1Graphics = img1.getGraphics();
            img1Graphics.drawImage(img2, x, y, img2.getWidth(), img2.getHeight(), null);
            isSuccess = ImageIO.write(img1, formatName, new File(destPath));
        } finally {
            if (img1Graphics != null) {
                img1Graphics.dispose();
            }
        }
        return isSuccess;
    }

    /**
     * 判断是否需要重新画最大边框的图片背景
     * @param img1  图片1
     * @param img2  图片2
     * @param x 图片2起始X坐标
     * @param y 图片2起始Y坐标
     * @return
     */
    private static boolean needReptain(BufferedImage img1, BufferedImage img2,
                                       int x, int y) {
        if (img1 == null || img2 == null || img1.getWidth() <= 0 || img1.getHeight() <= 0
                || img2.getWidth() <= 0 || img2.getHeight() <= 0) {
            throw new IllegalArgumentException("图片信息不正确");
        }
        int w1 = img1.getWidth();
        int h1 = img1.getHeight();
        int w2 = img2.getWidth();
        int h2 = img2.getHeight();
        return x < 0 || y < 0 || w1 - x < w2 || h1 - y < h2;
    }
}
