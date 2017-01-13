package com.example.gearfitfreeboxtelec;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;

import com.samsung.android.sdk.cup.ScupButton;
import com.samsung.android.sdk.cup.ScupDialog;
import com.samsung.android.sdk.cup.ScupLabel;

public class GearFitFreeboxRemoteDialog extends ScupDialog implements ScupButton.ClickListener {
	
	private final String VOL_DEC = "vol_dec";
	private final String VOL_INC = "vol_inc";
	private final String PRGM_INC = "prgm_inc";
	private final String PRGM_DEC = "prgm_dec";
	private final String POWER = "power";
	private final String OK = "ok";
	private final String UP = "up";
	private final String RIGHT = "right";
	private final String DOWN = "down";
	private final String LEFT = "left";
	private final String FREE = "home";
	private final String INFO = "yellow";
	private final String BACK = "red";
	private final String MENU = "green";
	
	MainActivity activity = null;
	HashMap<ScupButton,String> actionByButton = new HashMap<ScupButton, String>();
	public GearFitFreeboxRemoteDialog(Context context,MainActivity activity) {
		super(context);
		this.activity = activity;
	}

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setBackEnabled(true);
		
		ScupButton btVolDown = new ScupButton(this);
		btVolDown.setText("-");
		btVolDown.setTextSize(10);
		btVolDown.setAlignment(ScupButton.ALIGN_LEFT | ScupButton.ALIGN_VERTICAL_CENTER);
		btVolDown.setHeight(ScupButton.FILL_DIALOG);
		btVolDown.setClickListener(this);
		btVolDown.show();
		actionByButton.put(btVolDown, VOL_DEC);
		
		ScupLabel helloLabel = new ScupLabel(this);
		helloLabel.setText("Vol");
		helloLabel.setAlignment(ScupLabel.ALIGN_LEFT | ScupLabel.ALIGN_VERTICAL_CENTER);
		helloLabel.setHeight(ScupLabel.FILL_DIALOG);
		helloLabel.setPadding(5, 0, 5, 0);
		helloLabel.show();
		
		ScupButton btVolUp = new ScupButton(this);
		btVolUp.setText("+");
		btVolUp.setTextSize(10);
		btVolUp.setAlignment(ScupButton.ALIGN_LEFT | ScupButton.ALIGN_VERTICAL_CENTER);
		btVolUp.setHeight(ScupButton.FILL_DIALOG);
		btVolUp.setClickListener(this);
		btVolUp.show();
		actionByButton.put(btVolUp, VOL_INC);
		
		
		ScupLabel sep = new ScupLabel(this);
		sep.setText("|");
		sep.setAlignment(ScupButton.ALIGN_CENTER);
		sep.setHeight(ScupLabel.FILL_DIALOG);
		sep.show();
		
		
		ScupButton btPrDown = new ScupButton(this);
		btPrDown.setText("-");
		btPrDown.setTextSize(10);
		btPrDown.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		btPrDown.setHeight(ScupButton.FILL_DIALOG);
		btPrDown.setClickListener(this);
		btPrDown.show();
		actionByButton.put(btPrDown, PRGM_DEC);
		
		ScupLabel pr = new ScupLabel(this);
		pr.setText("Ch");
		pr.setPadding(5, 0, 5, 0);
		pr.setAlignment(ScupLabel.ALIGN_RIGHT | ScupLabel.ALIGN_VERTICAL_CENTER);
		pr.setHeight(ScupLabel.FILL_DIALOG);
		pr.show();
		
		ScupButton btPrUp = new ScupButton(this);
		btPrUp.setText("+");
		btPrUp.setTextSize(10);
		btPrUp.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		btPrUp.setHeight(ScupButton.FILL_DIALOG);
		btPrUp.setClickListener(this);
		btPrUp.show();
		actionByButton.put(btPrUp, PRGM_INC);
		
		ScupButton btON = new ScupButton(this);
		btON.setText("ON   ");
		btON.setTextSize(10);
		btON.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		btON.setHeight(ScupButton.FILL_DIALOG);
		btON.setClickListener(this);
		btON.show();
		actionByButton.put(btON, POWER);
		
		sep = new ScupLabel(this);
		sep.setText("|   ");
		sep.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		sep.setHeight(ScupLabel.FILL_DIALOG);
		sep.show();
		
		ScupButton btFree = new ScupButton(this);
		btFree.setText("Free ");
		btFree.setTextSize(10);
		btFree.setTextColor(Color.RED);
		btFree.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		btFree.setHeight(ScupButton.FILL_DIALOG);
		btFree.setClickListener(this);
		btFree.show();
		actionByButton.put(btFree, FREE);
		
		sep = new ScupLabel(this);
		sep.setText("| ");
		sep.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		sep.setHeight(ScupLabel.FILL_DIALOG);
		sep.show();
		
		ScupButton btInfo = new ScupButton(this);
		btInfo.setText("INFO ");
		btInfo.setTextSize(10);
		btInfo.setTextColor(Color.YELLOW);
		btInfo.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		btInfo.setHeight(ScupButton.FILL_DIALOG);
		btInfo.setClickListener(this);
		btInfo.show();
		actionByButton.put(btInfo, INFO);
		
		ScupButton btLeft = new ScupButton(this);
		btLeft.setText("  <  ");
		btLeft.setTextSize(10);
		btLeft.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		btLeft.setHeight(ScupButton.FILL_DIALOG);
		btLeft.setClickListener(this);
		btLeft.show();
		actionByButton.put(btLeft, LEFT);
		
		ScupButton btRight = new ScupButton(this);
		btRight.setText("  >  ");
		btRight.setTextSize(10);
		btRight.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		btRight.setHeight(ScupButton.FILL_DIALOG);
		btRight.setClickListener(this);
		btRight.show();
		actionByButton.put(btRight, RIGHT);
		
		ScupButton btUp = new ScupButton(this);
		btUp.setText("  ^  ");
		btUp.setTextSize(10);
		btUp.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		btUp.setHeight(ScupButton.FILL_DIALOG);
		btUp.setClickListener(this);
		btUp.show();
		actionByButton.put(btUp, UP);
		
		ScupButton btDown = new ScupButton(this);
		btDown.setText("  v ");
		btDown.setTextSize(10);
		btDown.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		btDown.setHeight(ScupButton.FILL_DIALOG);
		btDown.setClickListener(this);
		btDown.show();
		actionByButton.put(btDown, DOWN);
		
		sep = new ScupLabel(this);
		sep.setText("| ");
		sep.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		sep.setHeight(ScupLabel.FILL_DIALOG);
		sep.show();
		
		ScupButton btOK = new ScupButton(this);
		btOK.setText("OK");
		btOK.setTextSize(10);
		btOK.setAlignment(ScupButton.ALIGN_RIGHT | ScupButton.ALIGN_VERTICAL_CENTER);
		btOK.setHeight(ScupButton.FILL_DIALOG);
		btOK.setClickListener(this);
		btOK.show();
		actionByButton.put(btOK, OK);
		
		ScupButton btBack = new ScupButton(this);
		btBack.setText("  BACK  ");
		btBack.setTextSize(10);
		btBack.setTextColor(Color.RED);
		btBack.setAlignment(ScupButton.ALIGN_CENTER);
		btBack.setHeight(ScupButton.FILL_DIALOG);
		btBack.setClickListener(this);
		btBack.show();
		actionByButton.put(btBack, BACK);
		
		sep = new ScupLabel(this);
		sep.setText("  | ");
		sep.setAlignment(ScupButton.ALIGN_CENTER);
		sep.setHeight(ScupLabel.FILL_DIALOG);
		sep.show();
		
		ScupButton btMenu = new ScupButton(this);
		btMenu.setText("  MENU  ");
		btMenu.setTextSize(10);
		btMenu.setTextColor(Color.GREEN);
		btMenu.setAlignment(ScupButton.ALIGN_CENTER);
		btMenu.setHeight(ScupButton.FILL_DIALOG);
		btMenu.setClickListener(this);
		btMenu.show();
		actionByButton.put(btMenu, MENU);
		
		setBackPressedListener(new BackPressedListener() {
			@Override
			public void onBackPressed(ScupDialog arg0) {
				// TODO Auto-generated method stub
				activity.finish();
				finish();
			}
		});
		
		
	}

	@Override
	public void onClick(ScupButton button) {
		final ScupButton localButton = button;
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	GearFitFreeboxRemoteDialog.this.sendOrder(localButton);
		        
		    }
		});
		thread.start();
		
	}
	
	public void sendOrder(ScupButton button){
		String action = actionByButton.get(button);
		String errorMsg = null;
        try {
        	String error = GearFitFreeboxRemoteDialog.this.activity.sendOrder(action, "false");
        	if(!error.equals("OK")){
        		errorMsg = error;
        	}
        } catch (Exception e) {
        	errorMsg = e.getMessage();
        }
        if(errorMsg != null){
        	GearFitFreeboxRemoteDialog.this.showToast(errorMsg, TOAST_DURATION_LONG);
        }
	}
	
	
}
