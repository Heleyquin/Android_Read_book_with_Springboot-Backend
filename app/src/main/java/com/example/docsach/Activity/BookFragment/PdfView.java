package com.example.docsach.Activity.BookFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.docsach.API.ReaderApi;
import com.example.docsach.API.SachApi;
import com.example.docsach.Model.DTO.LichSuDocRequest;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;
import com.example.docsach.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PdfView extends AppCompatActivity {

    private PDFView pdfView;
    private Sach sach;
    private Reader reader;
    private int pageNumber;
    private int isTry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pdf_view);

        setControl();
        setDataIntent();
        loadPdfFile(sach.getUrlFile());
    }
    private void setDataIntent() {
        Intent intent = getIntent();
        sach = (Sach) intent.getSerializableExtra("sach");
        reader = (Reader) intent.getSerializableExtra("reader");
        isTry = (int) intent.getSerializableExtra("try");
    }

    private void setControl() {
        pdfView = findViewById(R.id.pdfView);
    }

    private void loadPdfFile(String fileName){
        SachApi.sachApi.getPdfFile(fileName).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    InputStream inputStream = response.body().byteStream();
                    displayPDF(inputStream);
                }
                else {
                    Log.e("API_ERROR", "Failed to download PDF");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void displayPDF(InputStream inputStream){
        if(isTry == 1){
            Double pageSize = Math.floor(pageNumber*0.07);
            int[] pageNumbers = new int[pageSize.intValue()];
            for (int i = 0; i < pageSize; i++) {
                pageNumbers[i] = i;
            }
            pdfView.fromStream(inputStream)
                    .pages(0,1,2,3,4,5,6,7,8,9,10)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .showMinimap(true)
                    .showPageWithAnimation(true)
                    .load();
        }
        else if (isTry == 0){
            LichSuDocRequest lichSuDocRequest = new LichSuDocRequest(reader, sach);
            ReaderApi.readerApi.docSach(lichSuDocRequest).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        pdfView.fromStream(inputStream)
                                .enableSwipe(true)
                                .enableDoubletap(true)
                                .showMinimap(true)
                                .showPageWithAnimation(true)
                                .load();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

}