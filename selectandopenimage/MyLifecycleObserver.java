package com.example.selectandopenimage;

import android.net.Uri;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class MyLifecycleObserver implements DefaultLifecycleObserver {
    private final ActivityResultRegistry mRegistry;
    private ActivityResultLauncher<String> mGetContent;

    MyLifecycleObserver(@NonNull ActivityResultRegistry registry){
        mRegistry=registry;
    }
    public void onCreate(@NonNull LifecycleOwner owner){
        mGetContent =mRegistry.register("key", owner, new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        SharingData.uri=result;
                    }
                });
    }
    public void selectImage(){
        //Open Activity to select Image
        mGetContent.launch("image/*");
    }
}
