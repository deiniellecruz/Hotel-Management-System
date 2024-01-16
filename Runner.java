import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Runner {
	public static void main(String[] args) {
		Random r = new Random();
		Hotel hotel = new Hotel(r);
		Scanner sc = new Scanner(System.in);
		CustomDate currentDate = new CustomDate(3, 10, 2022);
		
		for(int i = 0; i < 20; i++) {
			hotel.createRoom(r.nextInt(4) + 1, r.nextInt(4) + 1, r.nextInt(2), r.nextInt(2), (r.nextInt(4) + 1) * 500);
		}
		
		FrameMenu menu = new FrameMenu(hotel, currentDate);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setVisible(true);
	}
}