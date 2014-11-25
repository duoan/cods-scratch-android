快速开发不可或缺的11个工具类
=====

Android快速开发不可或缺的11个辅助类，其中10个来自张鸿洋的博客，1个是我平时积攒的，复制粘贴到你的项目里，添加上包名就可以直接使用，能提高开发速度。

代码片段

    // 缩放/裁剪图片
    public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight)
    { 
       // 获得图片的宽高
       int width = bm.getWidth();
       int height = bm.getHeight();
       // 计算缩放比例
       float scaleWidth = ((float) newWidth) / width;
       float scaleHeight = ((float) newHeight) / height;
       // 取得想要缩放的matrix参数
       Matrix matrix = new Matrix();
       matrix.postScale(scaleWidth, scaleHeight);
       // 得到新的图片
       Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
       return newbm;
    }
