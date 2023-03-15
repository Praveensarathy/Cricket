package com.consecutiveRuns;

import java.util.Arrays;
import java.util.Scanner;

public class Cricket {
	Scanner sc;
	int noOfOvers;
	int strike =1;
	String runBoard[];
	Players players[];
	int w =3;
	Cricket(){
		sc = new Scanner(System.in);
		players = new Players[10];
	}
	public static void main(String[] args) {
		Cricket cric = new Cricket();
		if(cric.initializer()) {
			cric.maxScore();
			cric.print();
		}
	}
	private void print() {
		int i=1;
		System.out.println("Player Table ");
		for(Players player : players) {
			if(player.runs.size() == 0)
				player.runs.add(0);
			System.out.println("player "+i +":"+ player.runs +" & Consecutive run is :  "+ player.conRuns);
			i++;
			if(i==w)
				break;
		}
	}
	public Players ball(int run , Players p) {
		if(!(run == 'w' ||run == 'W') && !(run == 'd' ||run == 'D') ) {
			if(run%2==0) {
				if(p.runs.size() ==0)
					p.runs.add(run - '0');
				else
					p.runs.add(p.runs.get(p.runs.size()-1) +(run -'0'));
				p.conRuns += run -'0';
				
			}
			else {
				if(p.runs.size() ==0)
					p.runs.add(run - '0');
				else{ 
					p.runs.add(p.runs.get(p.runs.size()-1)+(run -'0'));
				}
				p.conRuns += run -'0';
				strike = strike == 1 ? 2 :1;
			}
		}
		else {
			if(run == 'd' ||run == 'D') 
				p.conRuns =0;
		}
		return p;
	}
	private void maxScore() {
		Players p1 = new Players(1);
		Players p2 = new Players(2);
		int max=0;
		for(String over : runBoard) {
			for(char run : over.toCharArray()) {
				if(strike == 1) {
					if(run == 'O' ||run == 'o') {
						players[p1.batingDown-1] = p1;
						p1 = new Players(w++);
					}
					else
						p1 =  ball(run,p1);
				}
				else {
					if(run == 'O' ||run == 'o') {
						players[p2.batingDown-1] = p2;
						p2 = new Players(w++);
					}
					else
						p2 = ball(run,p2);
				}
			}
			strike = strike == 1 ? 2 :1;
			int cons= p1.conRuns<p2.conRuns ? p2.conRuns :p1.conRuns;
			max = max<cons?cons : max;
		}
		players[p1.batingDown-1] = p1;
		players[p2.batingDown-1] = p2;
		System.out.print("The Maximam consecutive Number is : ");
		System.out.println(max);
		return;
	}
	
	private boolean initializer() {
		System.out.println("How many Overs");
		noOfOvers = sc.nextInt();
		System.out.println("Runs :: 1 || 2 || 3 || 4 || 6 || d || w || o");
		runBoard = new String[noOfOvers];
		
		for(int i=1;i<=noOfOvers;i++) {
			System.out.println(i +" " + "over");
			String over = sc.next();
			if(over.length() <6) {
				System.out.println("Invalid Input");
				return false;
			}
			else {
				int count=0,valid=0;
				for(char run : over.toCharArray()) {
					if(!(run == 'w' ||run == 'W')) {
						count++;
					}
					if(count == 6) {
						valid=1;
						continue;
					}
					if(valid==1) {
						System.out.println("Invalid Input");
						return false;
					}
				}
				if(count < 6) {
					System.out.println("Invalid Input");
					return false;
				}
			}
			runBoard[i-1] = over;
		}
		System.out.println("Scores Board");
		for(String s : runBoard) {
			System.out.println(Arrays.toString(s.toCharArray()));
		}
		return true;
	}

}
