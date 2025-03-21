package com.treasurehunting.java;

import com.treasurehunting.java.utils.Preferences;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.net.URL;

public class DesktopLauncher extends JFrame {

    private BufferStrategy bs;
    private GamePanel gp;

    public DesktopLauncher() {
        URL iconURL = getClass().getResource("/com/treasurehunting/assets/GameLogo.png");
        ImageIcon icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());
        setTitle("Treasure Hunting");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Khi người dùng click vào Close Button thì Power off chương trình
        setIgnoreRepaint(true); //Không tự động gọi hàm Repaint nữa, phải tự quản lý việc vẽ lại màn hình, dùng Buffer Strategy
        pack(); //Tự động điều chỉnh kích thước phù hợp với Component bên trong
        setLocationRelativeTo(null); //Căn giữa cửa sổ trên màn hình
        setResizable(false); //Tắt Resize Button
        setVisible(true); //Hiển thị cửa sổ
    }

    @Override
    public void addNotify() {
        super.addNotify();

        createBufferStrategy(2); //Double buffering
        bs = getBufferStrategy();
        gp = new GamePanel(bs, Preferences.GAME_WIDTH, Preferences.GAME_HEIGHT);
        add(gp);
        setContentPane(gp);
    }

}
