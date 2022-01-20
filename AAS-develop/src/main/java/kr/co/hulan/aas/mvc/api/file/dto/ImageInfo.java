package kr.co.hulan.aas.mvc.api.file.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfo {
    private int width;
    private int heigth;
    private int type;
    private long fileSize;
}

