package kr.co.hulan.aas.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Matrix {

  private static Logger logger = LoggerFactory.getLogger(Matrix.class);

  public static int DEFAULT_ROW = 3;
  public static int DEFAULT_COL = 4;

  private int row;
  private int col;
  private int[][] matrix;

  public Matrix(){
    this(DEFAULT_ROW, DEFAULT_COL );
  }

  public Matrix(int row, int col){
    this.row = row;
    this.col = col;
    matrix = new int[row][col];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        matrix[i][j] = 0;
      }
    }
  }


  public void setData(int startX, int startY, int width, int height){
    int zeroBaseStartRow = startY-1;
    int zeroBaseStartCol = startX-1;
    int zeroBaseMaxRow = zeroBaseStartRow+height;
    int zeroBaseMaxCol = zeroBaseStartCol+width;
    for (int i = zeroBaseStartRow; i < Math.min(row, zeroBaseMaxRow) ; i++) {
      for (int j = zeroBaseStartCol; j < Math.min(col,zeroBaseMaxCol) ; j++) {
        matrix[i][j] = 1;
      }
    }
  }

  public boolean addEnable(int startX, int startY, int width, int height){
    int zeroBaseStartRow = startY-1;
    int zeroBaseStartCol = startX-1;
    int zeroBaseMaxRow = zeroBaseStartRow+height;
    int zeroBaseMaxCol = zeroBaseStartCol+width;
    if( zeroBaseMaxRow > row || zeroBaseMaxCol > col ){
      return false;
    }
    for (int i = zeroBaseStartRow; i < Math.min(row, zeroBaseMaxRow) ; i++) {
      for (int j = zeroBaseStartCol; j < Math.min(col,zeroBaseMaxCol) ; j++) {
        if( matrix[i][j] == 1 ){
          return false;
        }
      }
    }
    return true;
  }

  public void print(){
    StringBuilder sb = new StringBuilder();
    sb.append("\n------------------------------\n");
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        sb.append(String.format("%4d", matrix[i][j] )) ;
      }
      sb.append("\n");
    }
    sb.append("------------------------------\n");
    logger.debug(sb.toString()) ;
  }

  public static void main(String argsp[]){
    Matrix mat = new Matrix();
    mat.setData(1,1,3,2);
    mat.print();
    System.out.println("-----------------------");
    //mat.setData(4,1,1,2);
    mat.print();
    System.out.println("-----------------------");

    System.out.println(mat.addEnable(3,3, 1, 1));
    /*
    mat = new Matrix();
    mat.setData(4,1,1,2);
    mat.print();
    System.out.println("-----------------------");
     */

  }
}
