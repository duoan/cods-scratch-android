package xyz.anduo.caculate;

import java.util.Stack;

import xyz.anduo.caculate.R;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author anduo
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	private static final String ACTIVITY_TAG = "MainActivity";

	// 操作符栈
	Stack<String> optr = new Stack<String>();
	// 运算数栈
	Stack<Double> opnd = new Stack<Double>();

	// 显示的文本狂
	TextView display = null;
	TextView answer = null;

	int index_begin = -1;

	// 是否进行计算的标识变量
	boolean press_answer_flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d(ACTIVITY_TAG, "开始初始化布局");

		// 文本框
		display = (TextView) findViewById(R.id.output_window);
		answer = (TextView) findViewById(R.id.answer_window);

		// 按钮1-9
		Button num0 = (Button) findViewById(R.id.zero_button);
		Button num1 = (Button) findViewById(R.id.one_button);
		Button num2 = (Button) findViewById(R.id.two_button);
		Button num3 = (Button) findViewById(R.id.three_button);
		Button num4 = (Button) findViewById(R.id.four_button);
		Button num5 = (Button) findViewById(R.id.five_button);
		Button num6 = (Button) findViewById(R.id.six_button);
		Button num7 = (Button) findViewById(R.id.seven_button);
		Button num8 = (Button) findViewById(R.id.eight_button);
		Button num9 = (Button) findViewById(R.id.nine_button);

		// 按钮.
		Button point = (Button) findViewById(R.id.point_button);

		// 按钮回退、等于
		Button btnDel = (Button) findViewById(R.id.backspace_button);
		Button btnEql = (Button) findViewById(R.id.equal_button);

		// 运算符按钮
		Button btnPlu = (Button) findViewById(R.id.plus_button);
		Button btnMin = (Button) findViewById(R.id.minus_button);
		Button btnMul = (Button) findViewById(R.id.product_button);
		Button btnDiv = (Button) findViewById(R.id.divisor_button);
		Button btnLeft = (Button) findViewById(R.id.left_bracket_button);
		Button btnRight = (Button) findViewById(R.id.right_bracket_button);

		Log.d(ACTIVITY_TAG, "为按钮注册事件监听");
		// 为按钮注册点击监听事件
		num0.setOnClickListener(this);
		num1.setOnClickListener(this);
		num2.setOnClickListener(this);
		num3.setOnClickListener(this);
		num4.setOnClickListener(this);
		num5.setOnClickListener(this);
		num6.setOnClickListener(this);
		num7.setOnClickListener(this);
		num8.setOnClickListener(this);
		num9.setOnClickListener(this);
		point.setOnClickListener(this);

		btnDel.setOnClickListener(this);
		btnEql.setOnClickListener(this);

		btnPlu.setOnClickListener(this);
		btnMin.setOnClickListener(this);
		btnMul.setOnClickListener(this);
		btnDiv.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {

		// 获得点击的按钮
		Button btn = (Button) view;

		// 活的
		String show = display.getText().toString();

		String temp = null;

		Log.d(ACTIVITY_TAG, "点击了按钮" + btn.getText());

		if (btn.getText().equals("0") || btn.getText().equals("1")
				|| btn.getText().equals("2") || btn.getText().equals("3")
				|| btn.getText().equals("4") || btn.getText().equals("5")
				|| btn.getText().equals("6") || btn.getText().equals("7")
				|| btn.getText().equals("8") || btn.getText().equals("9")
				|| btn.getText().equals(".")) {
			// 处理数字和小数点

			if (press_answer_flag) {// 如果上一次是计算了结果的，那么这一次按键先要将显示屏和运算符栈、数字栈清空，运算表示位复位
				display.setText("");
				answer.setText("");
				optr.clear();
				opnd.clear();

				index_begin = -1;
				press_answer_flag = false;
				show = "";
			}
			show += btn.getText();
			display.setText(show);
			if (index_begin == -1) {
				index_begin = show.lastIndexOf(btn.getText().toString());
				// Toast.makeText(this,
				// "index_begin is changed from -1 to "+index_begin,
				// Toast.LENGTH_LONG).show();
			}
		} else if (btn.getText().equals("+") || btn.getText().equals("-")
				|| btn.getText().equals("*") || btn.getText().equals("/")
				|| btn.getText().equals("(") || btn.getText().equals(")")) {
			// 处理运算符
			if (press_answer_flag) {
				display.setText(answer.getText());
				index_begin = -1;
				press_answer_flag = false;
				show = answer.getText().toString();
				answer.setText("");
			}
			if (index_begin != -1) {
				temp = show.substring(index_begin);
				opnd.push(Double.valueOf(temp).doubleValue());
				index_begin = -1;
				// Toast.makeText(this,
				// "temp is"+temp+". push into opnd,the number is "+Double.valueOf(temp).doubleValue(),
				// Toast.LENGTH_LONG).show();
			}
			show += btn.getText();
			display.setText(show);
			
			//获得当前运算符
			temp = btn.getText().toString();
			
			//根据运算符的优先级进行运算符栈、数字栈的处理
			while (!optr.empty() && !lesser(optr.peek(), temp)) {
				switch (precede(optr.peek(), temp)) {
				case '<':
					optr.push(temp);
					break;
				case '=':
					optr.pop();
					return;
				case '>':
					double a,
					b,
					c;
					b = opnd.pop();
					a = opnd.pop();
					String theta = optr.pop();
					c = operate(a, theta, b);
					opnd.push(c);
					break;
				case '#':
					show = "ERROR";
					display.setText(show);
					optr.clear();
					opnd.clear();
					break;
				}
			}
			if (optr.empty() || lesser(optr.peek(), temp)) {
				optr.push(temp);
			}

		} else if (btn.getText().equals("=")) {
			Log.d(ACTIVITY_TAG, "计算表达式的结果");
			//计算表达式的结果
			press_answer_flag = true;
			if (index_begin != -1) {
				temp = show.substring(index_begin);
				opnd.push(Double.valueOf(temp).doubleValue());
				index_begin = -1;
				// Toast.makeText(this,
				// "temp is"+temp+". push into opnd,the number is "+Double.valueOf(temp).doubleValue(),
				// Toast.LENGTH_LONG).show();
			}
			show += btn.getText().toString();
			display.setText(show);

			while (!optr.isEmpty()) {
				double a, b, c;
				b = opnd.pop();
				a = opnd.pop();
				String theta = optr.pop();
				c = operate(a, theta, b);
				opnd.push(c);
			}
			answer.setText(opnd.peek().toString());
		} else if (btn.getText().equals("C")) {
			//清楚屏幕
			Log.d(ACTIVITY_TAG, "清除");
			
			display.setText("");
			answer.setText("");
			optr.clear();
			opnd.clear();
			index_begin = -1;
			press_answer_flag = false;
		}
	}

	/**
	 * 比较运算符的优先级
	 * @param theta
	 * @param temp
	 * @return
	 */
	private boolean lesser(String theta, String temp) {
		switch (precede(theta, temp)) {
		case '<':
			return true;
		case '=':
			return false;
		case '>':
			return false;
		}
		return false;
	}
	
	/**
	 * 两个数的四则运算
	 * @param a
	 * @param theta
	 * @param b
	 * @return
	 */
	private double operate(double a, String theta, double b) {
		if (theta.equals("+"))
			return a + b;
		else if (theta.equals("-"))
			return a - b;
		else if (theta.equals("*"))
			return a * b;
		else if (theta.equals("/"))
			return a / b;
		return 0;
	}

	
	/**
	 * 运算符的优先级比较
	 * @param theta
	 * @param temp
	 * @return
	 */
	private char precede(String theta, String temp) {
		if (theta.equals("+")) {
			if (temp.equals("+"))
				return '>';
			else if (temp.equals("-"))
				return '>';
			else if (temp.equals("*"))
				return '<';
			else if (temp.equals("/"))
				return '<';
			else if (temp.equals("("))
				return '<';
			else if (temp.equals(")"))
				return '>';
		} else if (theta.equals("-")) {
			if (temp.equals("+"))
				return '>';
			else if (temp.equals("-"))
				return '>';
			else if (temp.equals("*"))
				return '<';
			else if (temp.equals("/"))
				return '<';
			else if (temp.equals("("))
				return '<';
			else if (temp.equals(")"))
				return '>';
		} else if (theta.equals("*")) {
			if (temp.equals("+"))
				return '>';
			else if (temp.equals("-"))
				return '>';
			else if (temp.equals("*"))
				return '>';
			else if (temp.equals("/"))
				return '>';
			else if (temp.equals("("))
				return '<';
			else if (temp.equals(")"))
				return '>';
		} else if (theta.equals("/")) {
			if (temp.equals("+"))
				return '>';
			else if (temp.equals("-"))
				return '>';
			else if (temp.equals("*"))
				return '>';
			else if (temp.equals("/"))
				return '>';
			else if (temp.equals("("))
				return '<';
			else if (temp.equals(")"))
				return '>';
		} else if (theta.equals("(")) {
			if (temp.equals("+"))
				return '<';
			else if (temp.equals("-"))
				return '<';
			else if (temp.equals("*"))
				return '<';
			else if (temp.equals("/"))
				return '<';
			else if (temp.equals("("))
				return '<';
			else if (temp.equals(")"))
				return '=';
		} else if (theta.equals(")")) {
			if (temp.equals("+"))
				return '>';
			else if (temp.equals("-"))
				return '>';
			else if (temp.equals("*"))
				return '>';
			else if (temp.equals("/"))
				return '>';
			else if (temp.equals("("))
				return '#'; // 出现错误
			else if (temp.equals(")"))
				return '>';
		}
		return '#';
	}

}
