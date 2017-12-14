package com.example.shivani.aspire1;

        import android.Manifest;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.pm.PackageManager;
        import android.database.CursorJoiner;
        import android.graphics.Color;
        import android.hardware.Camera;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.Fragment;
        import android.support.v4.content.ContextCompat;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.pnikosis.materialishprogress.ProgressWheel;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;


public class TabOne extends Fragment {

    private boolean autoWhiteBalance, autoExposureLock, videoSnapshot, videoStablisation, zoom, smoothZoom;
    private boolean autoWhiteBalance2, autoExposureLock2, videoSnapshot2, videoStablisation2, zoom2, smoothZoom2;
    private  ArrayList<Content> array1;
    private ArrayList<Content> array2;
    private ProgressDialog mProgressDialog;
    ProgressWheel progressWheel;
    ListView lv1,lv2;
    CustomAdapter adapter,adapter2;
    TextView textView1,textView2;

    public TabOne() {
        // Required empty public constructor
    }


    public static TabOne newInstance(String param1, String param2) {
        TabOne fragment = new TabOne();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkCamerapermission();
       /* */
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tab_one,container,false);
        progressWheel = (ProgressWheel)view.findViewById(R.id.progress_wheel);
        progressWheel.setVisibility(View.VISIBLE);
        textView1=(TextView)view.findViewById(R.id.t1);
        textView2=(TextView)view.findViewById(R.id.t2);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        
       
        new MyAsyncCamera().execute();
      
        return view;
    }
    private class MyAsyncCamera extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            LayoutInflater inflater = (LayoutInflater)
                    getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.toast, (ViewGroup)
                    getActivity().findViewById(R.id.layout));
            //LinearLayout layout=(LinearLayout)getL.findViewById(R.id.layout);
            Toast toast=new Toast(getActivity());
            toast.setView(layout);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0,100);
            toast.show(); 
           /* mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Loading...");
           mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();*/
          
           // Toast.makeText(getActivity(),,Toast.LENGTH_SHORT).set.setGravity(Gravity.CENTER,0,100);
        }
            @Override
        protected Void doInBackground(Void... params) {
            array1= getBackCameraInfo();
            array2= getFrontCameraInfo();
                return  null;
        }
        
        @Override
        protected void onPostExecute(Void args) {
            progressWheel.setVisibility(View.GONE);
            lv1= (ListView)getActivity().findViewById(R.id.list1);
            adapter=new CustomAdapter(getActivity(),array1);
            lv1.setAdapter(adapter);
            lv2= (ListView)getActivity().findViewById(R.id.list2);
            adapter2=new CustomAdapter(getActivity(),array2);
            lv2.setAdapter(adapter2);
            textView1.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
          
           // mProgressDialog.dismiss();
            // Runs on the UI thread after doInBackground()
        }
    }
   
    private ArrayList<Content> getBackCameraInfo() {
        ArrayList<Content> itemList=new ArrayList<>();
        Content info=new Content("Resolution",getBackCameraResolution());
        itemList.add(info);
        info=new Content("Video Resolution",getBackVideoResolution());
        itemList.add(info);
        info=new Content("Focal Length",getFocalLengthBackCamera());
        itemList.add(info);
        info=new Content("Focus Modes",getFocusModesBackCamera()+"");
        itemList.add(info);
        info=new Content("Flash",getFlashBackCamera());
        itemList.add(info);
       info=new Content("Video Stabilization",getVideoStablizationBackCamera());
        itemList.add(info);
        info=new Content("Zoom",getZoomBackCamera());
        itemList.add(info);
        info=new Content("Smooth Zoom",getSmoothZoomBackCamera());
        itemList.add(info);
        info=new Content("Auto Exposure Locking",getAutoExposureBackCamera());
        itemList.add(info);
        info=new Content("Auto White Balance Lock",getAutoWhiteBalanceBackCamera());
        itemList.add(info);
        return itemList;}
    private ArrayList<Content> getFrontCameraInfo() {
        ArrayList<Content> itemList=new ArrayList<>();
        Content info=new Content("Resolution",getFrontCameraResolution());
        itemList.add(info);
        info=new Content("Video Resolution",getFrontVideoResolution());
        itemList.add(info);
        info=new Content("Focal Length",getFocalLengthFrontCamera());
        itemList.add(info);
        info=new Content("Focus Modes",getFocusModesFrontCamera()+"");
        itemList.add(info);
       info=new Content("Video Snapshot",getVideoSnapshotFrontCamera());
        itemList.add(info);
        info=new Content("Video Stabilization",getVideoStablizationFrontCamera());
        itemList.add(info);
        info=new Content("Zoom",getZoomFrontCamera());
        itemList.add(info);
        info=new Content("Smooth Zoom",getSmoothZoomFrontCamera());
        itemList.add(info);
        info=new Content("Auto Exposure Locking",getAutoExposureFrontCamera());
        itemList.add(info);
        info=new Content("Auto White Balance Lock",getAutoWhiteBalanceFrontCamera());
        itemList.add(info);
        return itemList;}
    @Override
    public String toString() {
        return "Camera";

    }

    public void checkCamerapermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            }
        }
    }
    public String getFlashBackCamera(){
        Camera camera=Camera.open(0);
        android.hardware.Camera.Parameters params = camera.getParameters();
        String flash=  params.getFlashMode();
        camera.release();
        return flash;

    }
    public String getFlashFrontCamera(){
        Camera camera=Camera.open(1);
        android.hardware.Camera.Parameters params = camera.getParameters();
        String flash2=  params.getFlashMode();
        camera.release();
        return flash2;
    }
    public String getFocalLengthBackCamera(){
        Camera camera=Camera.open(0);
        android.hardware.Camera.Parameters params = camera.getParameters();
        Float focal=  params.getFocalLength();
        camera.release();
        return focal+"\t"+" mm";
    }
    public String getFocalLengthFrontCamera(){
        Camera camera=Camera.open(1);
        android.hardware.Camera.Parameters params = camera.getParameters();
        Float focal2=  params.getFocalLength();
        camera.release();
        return focal2+"\t"+" mm";
    }
    public List<String> getFocusModesBackCamera(){
        Camera camera=Camera.open(0);
        android.hardware.Camera.Parameters params = camera.getParameters();
        List<String> list=  params.getSupportedFocusModes();
        camera.release();
        return list;
    }
    public List<String> getFocusModesFrontCamera(){
        Camera camera=Camera.open(1);
        android.hardware.Camera.Parameters params = camera.getParameters();
        List<String> list2=  params.getSupportedFocusModes();
        camera.release();
        return list2;
    }
     public String getVideoSnapshotBackCamera(){
         Camera camera=Camera.open(0);
         Camera.Parameters params = camera.getParameters();
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
             videoSnapshot=  params.isVideoSnapshotSupported();
         }
         camera.release();
         if(videoSnapshot==true)
         {
             checkCamerapermission();
             return "Supported";
         }
         else
         {
             checkCamerapermission();
         return "Not Supported";}
     }
      public String getVideoSnapshotFrontCamera(){
          Camera camera=Camera.open(1);
          Camera.Parameters params = camera.getParameters();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
              videoSnapshot2=  params.isVideoSnapshotSupported();
          }
          camera.release();
          if(videoSnapshot2==true)
          {
              checkCamerapermission();
              return "Supported";
          }
          else
          {
              checkCamerapermission();
              return "Not Supported";}
      }
      public String getVideoStablizationBackCamera(){
          Camera camera=Camera.open(0);
          Camera.Parameters params = camera.getParameters();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
              videoStablisation=  params.isVideoStabilizationSupported();
          }
          camera.release();
          if(videoStablisation==true)
          {
              checkCamerapermission();
              return "Supported";
          }
          else
          {
              checkCamerapermission();
              return "Not Supported";}
      }
      public String getVideoStablizationFrontCamera(){
          Camera camera=Camera.open(1);
          Camera.Parameters params = camera.getParameters();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
              videoStablisation2=  params.isVideoStabilizationSupported();
          }
          camera.release();
          if(videoStablisation2==true)
          {
              checkCamerapermission();
              return "Supported";
          }
          else
          {
              checkCamerapermission();
              return "Not Supported";}
      }
      public String getZoomBackCamera(){
          Camera camera=Camera.open(0);
          android.hardware.Camera.Parameters params = camera.getParameters();
          zoom=  params.isZoomSupported();
          camera.release();
          if(zoom==true)
          {
              checkCamerapermission();
              return "Supported";
          }
          else
          {
              checkCamerapermission();
              return "Not Supported";}
      }
      public String getZoomFrontCamera(){
          Camera camera=Camera.open(1);
          android.hardware.Camera.Parameters params = camera.getParameters();
          zoom2=  params.isZoomSupported();
          camera.release();
          if(zoom2==true)
          {
              checkCamerapermission();
              return "Supported";
          }
          else
          {
              checkCamerapermission();
              return "Not Supported";}
      }
      public String getSmoothZoomBackCamera(){
          Camera camera=Camera.open(0);
          android.hardware.Camera.Parameters params = camera.getParameters();
          smoothZoom=  params.isSmoothZoomSupported();
          camera.release();
          if(zoom==true)
          {
              checkCamerapermission();
              return "Supported";
          }
          else
          {
              checkCamerapermission();
              return "Not Supported";}
      }
      public String getSmoothZoomFrontCamera(){
          Camera camera=Camera.open(1);
          android.hardware.Camera.Parameters params = camera.getParameters();
          smoothZoom2=  params.isSmoothZoomSupported();
          camera.release();
          if(smoothZoom2==true)
          {
              checkCamerapermission();
              return "Supported";
          }
          else
          {
              checkCamerapermission();
              return "Not Supported";}
      }
      public String getAutoExposureBackCamera(){
          Camera camera=Camera.open(0);
          Camera.Parameters params = camera.getParameters();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
              autoExposureLock=  params.getAutoExposureLock();
          }
          camera.release();
          if(autoExposureLock=true)
          {
              checkCamerapermission();
              return "Supported";
          }
          else
          {
              checkCamerapermission();
              return "Not Supported";}
      }
      public String getAutoExposureFrontCamera(){
          Camera camera=Camera.open(1);
          Camera.Parameters params = camera.getParameters();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
              autoExposureLock2=  params.getAutoExposureLock();
          }
          camera.release();
          if(autoExposureLock2=true)
          {
              checkCamerapermission();
              return "Supported";
          }
          else
          {
              checkCamerapermission();
              return "Not Supported";}
      }
      public String getAutoWhiteBalanceBackCamera(){
          Camera camera=Camera.open(0);
          Camera.Parameters params = camera.getParameters();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
              autoWhiteBalance=  params.getAutoWhiteBalanceLock();
          }
          camera.release();
          if(autoWhiteBalance=true)
          {
              checkCamerapermission();
              return "Supported";
          }
          else
          {
              checkCamerapermission();
              return "Not Supported";}
      }
      public String getAutoWhiteBalanceFrontCamera(){
          Camera camera=Camera.open(1);
          Camera.Parameters params = camera.getParameters();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
              autoWhiteBalance2=  params.getAutoWhiteBalanceLock();
          }
          camera.release();
          if(autoWhiteBalance2=true)
          {
              checkCamerapermission();
              return "Supported";
          }
          else
          {
              checkCamerapermission();
              return "Not Supported";
          }
        
      }
     /* autoExposureLock=  params.getAutoExposureLock();
      videoStablisation=  params.getVideoStabilization();
     
      zoom=   params.isZoomSupported();
      smoothZoom=   params.isSmoothZoomSupported();
      autoWhiteBalance=  params.isAutoWhiteBalanceLockSupported();
      checkCamerapermission();
      Camera camera2=Camera.open(1);
      android.hardware.Camera.Parameters params2 = camera2.getParameters();
      autoExposureLock2=  params2.getAutoExposureLock();
      videoStablisation2=  params2.getVideoStabilization();
      videoSnapshot2=  params2.isVideoSnapshotSupported();
      zoom2=   params2.isZoomSupported();
      smoothZoom2=   params2.isSmoothZoomSupported();
      autoWhiteBalance2=  params2.isAutoWhiteBalanceLockSupported();*/
    public String getBackCameraResolution(){
        Camera camera=Camera.open(0);    // For Back Camera
        android.hardware.Camera.Parameters params = camera.getParameters();
        List sizes = params.getSupportedPictureSizes();
        Camera.Size  result = null;
        float a=0;
        int b=0,c=0;

        ArrayList<Integer> arrayListForWidth = new ArrayList<Integer>();
        ArrayList<Integer> arrayListForHeight = new ArrayList<Integer>();

        for (int i=0;i<sizes.size();i++){
            result = (Camera.Size) sizes.get(i);
            arrayListForWidth.add(result.width);
            arrayListForHeight.add(result.height);
            // Log.debug("PictureSize", "Supported Size: " + result.width + "height : " + result.height);
            System.out.println("BACK PictureSize Supported Size: " + result.width + "height : " + result.height);
        }
        if(arrayListForWidth.size() != 0 && arrayListForHeight.size() != 0){
            System.out.println("Back max W :"+ Collections.max(arrayListForWidth));              // Gives Maximum Width
            System.out.println("Back max H :"+Collections.max(arrayListForHeight));                 // Gives Maximum Height
            System.out.println("Back Megapixel :"+( ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight))) / 1024000 ) );
            //  a=  ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight))) / 1024000;
            b=Collections.max(arrayListForWidth);
            c=Collections.max(arrayListForHeight);
            a=(b*c)/1024000;
        }
        camera.release();

        arrayListForWidth.clear();
        arrayListForHeight.clear();
        checkCamerapermission();
        return a+1+" MP"+"( "+b+" x "+c+" )";
    }
    public String getFrontCameraResolution(){
        Camera camera=Camera.open(1);
        android.hardware.Camera.Parameters params = camera.getParameters();
        List sizes = params.getSupportedPictureSizes();
        Camera.Size  result = null;
        float a=0;
        int b=0,c=0;

        ArrayList<Integer> arrayListForWidth = new ArrayList<Integer>();
        ArrayList<Integer> arrayListForHeight = new ArrayList<Integer>();

        for (int i=0;i<sizes.size();i++){
            result = (Camera.Size) sizes.get(i);
            arrayListForWidth.add(result.width);
            arrayListForHeight.add(result.height);
            // Log.debug("PictureSize", "Supported Size: " + result.width + "height : " + result.height);
            System.out.println("BACK PictureSize Supported Size: " + result.width + "height : " + result.height);
        }
        if(arrayListForWidth.size() != 0 && arrayListForHeight.size() != 0){
            System.out.println("Back max W :"+Collections.max(arrayListForWidth));              // Gives Maximum Width
            System.out.println("Back max H :"+Collections.max(arrayListForHeight));                 // Gives Maximum Height
            System.out.println("Back Megapixel :"+( ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight))) / 1024000 ) );
            //  a=  ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight))) / 1024000;
            b=Collections.max(arrayListForWidth);
            c=Collections.max(arrayListForHeight);
            a=(b*c)/1024000;
        }
        camera.release();

        arrayListForWidth.clear();
        arrayListForHeight.clear();
        checkCamerapermission();
        return a+1+" MP"+"( "+b+" x "+c+" )";
    }
    public String getBackVideoResolution(){
        Camera camera=Camera.open(0);    // For Back Camera
        android.hardware.Camera.Parameters params = camera.getParameters();
        List sizes = params.getSupportedVideoSizes();
        Camera.Size  result = null;
        float a=0;
        int b=0,c=0;

        ArrayList<Integer> arrayListForWidth = new ArrayList<Integer>();
        ArrayList<Integer> arrayListForHeight = new ArrayList<Integer>();

        for (int i=0;i<sizes.size();i++){
            result = (Camera.Size) sizes.get(i);
            arrayListForWidth.add(result.width);
            arrayListForHeight.add(result.height);
            // Log.debug("PictureSize", "Supported Size: " + result.width + "height : " + result.height);
            System.out.println("BACK PictureSize Supported Size: " + result.width + "height : " + result.height);
        }
        if(arrayListForWidth.size() != 0 && arrayListForHeight.size() != 0){
            System.out.println("Back max W :"+Collections.max(arrayListForWidth));              // Gives Maximum Width
            System.out.println("Back max H :"+Collections.max(arrayListForHeight));                 // Gives Maximum Height
            System.out.println("Back Megapixel :"+( ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight))) / 1024000 ) );
            //  a=  ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight))) / 1024000;
            b=Collections.max(arrayListForWidth);
            c=Collections.max(arrayListForHeight);
            a=(b*c)/1024000;
        }
        camera.release();

        arrayListForWidth.clear();
        arrayListForHeight.clear();
        checkCamerapermission();
        return a+" MP"+"( "+b+" x "+c+" )";
    }
    public String getFrontVideoResolution(){
        Camera camera=Camera.open(1);
        android.hardware.Camera.Parameters params = camera.getParameters();
        List sizes = params.getSupportedVideoSizes();
        Camera.Size  result = null;
        float a=0;
        int b=0,c=0;

        ArrayList<Integer> arrayListForWidth = new ArrayList<Integer>();
        ArrayList<Integer> arrayListForHeight = new ArrayList<Integer>();

        for (int i=0;i<sizes.size();i++){
            result = (Camera.Size) sizes.get(i);
            arrayListForWidth.add(result.width);
            arrayListForHeight.add(result.height);
            // Log.debug("PictureSize", "Supported Size: " + result.width + "height : " + result.height);
            System.out.println("BACK PictureSize Supported Size: " + result.width + "height : " + result.height);
        }
        if(arrayListForWidth.size() != 0 && arrayListForHeight.size() != 0){
            System.out.println("Back max W :"+Collections.max(arrayListForWidth));              // Gives Maximum Width
            System.out.println("Back max H :"+Collections.max(arrayListForHeight));                 // Gives Maximum Height
            System.out.println("Back Megapixel :"+( ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight))) / 1024000 ) );
            //  a=  ((Collections.max(arrayListForWidth)) * (Collections.max(arrayListForHeight))) / 1024000;
            b=Collections.max(arrayListForWidth);
            c=Collections.max(arrayListForHeight);
            a=(b*c)/1024000;
        }
        camera.release();

        arrayListForWidth.clear();
        arrayListForHeight.clear();
        checkCamerapermission();
        return a+" MP"+"( "+b+" x "+c+" )";
    }



}
