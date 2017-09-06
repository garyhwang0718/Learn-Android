package com.qs.aidldemo;

import com.qs.aidl.IPerson;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AIDLService extends Service{
	//�õ�һ��ϵͳ�Զ����ɵĴ����࣬Ȼ��ʵ�ִ�������Ҫ����ʵ�ֵ���������
	//��Ҳ�������Ƿ������ṩ�������ӿں���
	private IPerson.Stub stub = new IPerson.Stub() {
		
		@Override
		public String greet(String someone) throws RemoteException {
			return "hello "+someone;
		}
		
		@Override
		public String add(String a, String b) throws RemoteException {
			int aa = Integer.parseInt(a);
			int bb = Integer.parseInt(b);
			return String.valueOf(aa+bb);
		}
	};
	@Override
	public IBinder onBind(Intent intent) {
		System.out.println("data from client =="+intent.getExtras().getString("time"));
		return stub;
	}
	//���ǿ��Կ�����client����bindService֮�����ǵ�onCreate������������
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("AIDLServerDemo service onCreate------");
	}
	//���ǿ��Կ�����client����onDestory��Ҳ����ֱ�Ӱ����ؼ��˳�֮�����ǵ�onDestory������������
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("AIDLServerDemo service onDestroy------");
	}

}
