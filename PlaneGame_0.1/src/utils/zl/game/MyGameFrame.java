package utils.zl.game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * 飞机游戏的主窗口
 * @author 郑靓
 */
public class MyGameFrame extends Frame{

    Image planeImg = GameUtil.getImage("images/plane.png");
    Image bg = GameUtil.getImage("images/bg.jpg");

    Plane plane = new Plane(planeImg, 250, 250);
    Shell[] shells = new Shell[50];
    Explode bao;
    Date startTime = new Date();
    Date endTime;
    int period;

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.drawImage(bg, 0, 0, null);
        plane.drawSelf(g);
        for (int i = 0; i < shells.length; i++) {
            shells[i].draw(g);

            //飞机和炮弹的碰撞检测
            boolean peng = shells[i].getRect().intersects(plane.getRect());

            if (peng) {
                plane.live = false;
                if (bao == null) {
                    bao = new Explode(plane.x, plane.y);

                    endTime = new Date();
                    period = (int)(endTime.getTime() - startTime.getTime())/1000;
                }
                bao.draw(g);
            }

            if (!plane.live) {
                g.setColor(Color.red);
                Font f = new Font("宋体", Font.BOLD, 40);
                g.setFont(f);
                g.drawString("时间：" + period + "秒", (int) plane.x, (int) plane.y);
            }
        }

        g.setColor(c);
    }

    //反复重画窗口
    class PaintThread extends Thread {

        @Override
        public void run() {
            while (true) {
                repaint();  //重画窗口

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //定义键盘监听的内部类
    class KeyMonitor extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            plane.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            plane.minusDirection(e);
        }
    }

    /**
     * 初始化窗口
     */
    public void launchFrame() {
        //在游戏窗口打印标题
        this.setTitle("尚学堂学员_程序猿作品");
        //窗口默认不可见，设为可见
        this.setVisible(true);
        //窗口大小：宽度500，高度500
        this.setSize(Constant.GAME_WIDTH, Constant.GAME_HIGHT);
        //窗口左上角顶点的坐标位置
        this.setLocation(300,300);

        //增加关闭窗口监听，这样用户点击右上角关闭图标，可以关闭游戏程序
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        new PaintThread().start();  //启动重画窗口的线程
        addKeyListener(new KeyMonitor());   //增加键盘监听

        //初始化50个炮弹
        for (int i = 0; i < shells.length; i++) {
            shells[i] = new Shell();
        }
    }

    public static void main(String[] args) {
        MyGameFrame f = new MyGameFrame();
        f.launchFrame();
    }

    //双缓冲
    private Image offScreenImage = null;

    public void update(Graphics g) {
        if(offScreenImage == null)
            offScreenImage = this.createImage(500,500);//这是游戏窗口的宽度和高度

        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }
}
