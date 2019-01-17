package lexical_analyzer;
import java.io.*;


public class Lexical_analyzer {
	    static String prog;  
	    static char ch;  
	    static char[]token=new char[8];  
	    static int syn,p,m,n,sum;  
	    static String[] rwtable={"begin","if","then","while","do","end"};  
	    static int row=1,column=0,row_next=1,column_next=0;  
	    /** 
	     * @param args 
	     * @throws IOException  
	     */  
	    
	    public static String getString() {
	    	String xmlString;
	    	byte[] strBuffer = null;
	    	int    flen = 0;
	    	File xmlfile = new File("input.txt");  
	    	 try {
	    		InputStream in = new FileInputStream(xmlfile);
	    		flen = (int)xmlfile.length();
	    		strBuffer = new byte[flen];
	    		in.read(strBuffer, 0, flen);
	    	} catch (FileNotFoundException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (IOException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	xmlString = new   String(strBuffer);      //����Stringʱ������byte[]���ͣ�
	     
	    	return xmlString;
	    }
	    
	    public static void main(String[] args) throws IOException {  
	         //1�������ַ���  
	         //prog="begin  x:=9; if x>0  then   x:=2*x+1/3;end #";  
	         //1�����ļ��ж�ȡ�ַ���  
	        prog=getString();  
	         //2��ɨ�����  
	         p=0;  
	         do{  
	             scaner();  
	             switch(syn){  
	             case 11:System.out.print("("+syn+" , ");//���ʷ��ţ�Digit digit*  
	                     System.out.print(sum);  
	                     System.out.println(")"); 
	                     System.out.println(row+" "+column);
	                break;  
	             case -1:System.out.println("error!");  
	                break;  
	             default:  
	                     System.out.print("(");  
	                     System.out.print(syn);   
	                     System.out.print(" , ");  
	                     String str=new String(token);  
	                     System.out.print(str);  
	                     System.out.println(")");
	                     System.out.println(row+" "+column);
	             }  
	         }while(syn!=0);  
	           
	           
	    }  
	    
	    //ɨ�����  
	    private static void scaner() throws IOException {         
//	      1����ʼ��  
	    	sum=0;
	    	m=0;
	        for(int i=0;i<8;i++)  
	            token[i]=' ';
	        column=column_next;
	        row=row_next;
//	      2������ĸ  
	        ch=prog.charAt(p++);//charAt�ĵ�һ���ַ�Ӧ�ø�����0
	        column++;
	        column_next++;
	        while(ch==' '||ch=='\r'||ch=='\n'){//ע�⣬��debugģʽ�У������ܽ��س��ո�֮����ַ���ʾ����  
	            
	            if(ch==' ') {
	            	column++;
	            	column_next++;
	            }
	            else if(ch=='\n'){
	            	row++;
	            	row_next++;
	            	column=1;
	            	column_next=1;
	            }
	            ch=prog.charAt(p++);
	        }  
//	      3����ʼִ��ɨ��  
//	          1������ĸ  
//	                     ����ʶ�����鱣���ֱ�  
//	                         �鵽�����������ֱ�д�������  
//	                         û�鵽�� ���������������֣�д�������  
	        if(ch>='a'&&ch<='z'){  
	            //m=0;  
	            //��ȡ��������  
	            while((ch>='a'&&ch<='z')||(ch>='0'&&ch<='9')){  
	                token[m++]=ch;  
	                ch=prog.charAt(p++);
	                column_next++;
	            }  
	            token[m++]='\0';  
	            --p;  
	            column_next--;
	            syn=10;//���ʷ���Ϊletter(letter|digit)*  
	            //�ж����ĸ��ؼ���  
	            String newStr=new String(token);  
	            newStr=newStr.trim();//ȥ����β�ո�  
	            //System.out.println("newStr:"+newStr);  
	            for(n=0;n<6;n++){  
	                //System.out.println("rwtable:"+rwtable[n]);  
	                if(newStr.equals(rwtable[n])){  
	                    syn=n+1;  
	                    System.out.println("syn ��ֵ�ǣ�"+syn);  
	                    break;  
	                }  
	            }  
	            token[m++]='\0';  
	        }
	        
//	          2��������  
//	                         ȡ���֣��鳣�������������ֱ�д�������  
	        else if(ch>='0'&&ch<='9'){  
	            while(ch>='0'&&ch<='9'){  
	                sum=sum*10+ch-'0';  //��ASCII��ͬ
	                ch=prog.charAt(p++);  
	                column_next++;
	            }  
	            --p;  
	            column_next--;
	            syn=11;//digitdigit*  
	            token[m++]='\0';  
	        }  
//	          3�����������  
//	                         ��������ű����������֡�д�������  
//	          4������error  
//	      4���Ƿ��������  
//	              δ��������2  
//	              ������������  
	        else   
	        switch(ch){  
	            case'<':   
	                token[m++]=ch;  
	                ch=prog.charAt(p++);  
	                column_next++;
	                if(ch=='>'){  
	                    syn=21;//<>  
	                    token[m++]=ch; 
	                }  
	                else if(ch=='='){  
	                    syn=22;//<=  
	                    token[m++]=ch;  
	                }  
	                else{  
	                    syn=20;//<  
	                    --p;
	                    column_next--;
	                }
	             
	            break;  
	            case'>':  
	                token[m++]=ch;  
	                ch=prog.charAt(p++);  
	                column_next++;
	                if(ch=='='){  
	                    syn=24;//>=  
	                    token[m++]=ch; 
	                }  
	                else{  
	                    syn=23;//>  
	                    --p;
	                    column_next--;
	                }
	                break;  
	            case':':  
	                token[m++]=ch;  
	                ch=prog.charAt(p++);
	                column_next++;
	                if(ch=='='){  
	                    syn=18;//:=  
	                    token[m++]=ch;  
	                }  
	                else{  
	                    syn=17;//:  
	                    --p;
	                    column_next--;
	                }

	            break;  
	            case'+':  
	                syn=13;token[0]=ch;token[1]='\0';break;  
	            case'-':  
	                syn=14;token[0]=ch;token[1]='\0';break;  
	            case'*':  
	                syn=15;token[0]=ch;token[1]='\0';break;  
	            case'/':  
	                syn=16;token[0]=ch;token[1]='\0';break;  
	            case'=':  
	                syn=25;token[0]=ch;token[1]='\0';break;  
	            case';':  
	                syn=26;token[0]=ch;token[1]='\0';break;  
	            case'(':  
	                syn=27;token[0]=ch;token[1]='\0';break;  
	            case')':  
	                syn=28;token[0]=ch;token[1]='\0';break;  
	            case'#':  
	                syn=0;token[0]=ch;token[1]='\0';break;  
	            default:  
	                syn=-1;   
	                  
	        }  
	          
//	        File txt=new File("src/nihao.txt");  
//	           if(!txt.exists()){  
//	               txt.createNewFile();  
//	           }  
//	           byte[] bytes=new byte[token.length];//����һ����������Ҫת����char������ͬ��byte����  
//	           for(int i=0;i<bytes.length ;i++){//ѭ����char��ÿ��Ԫ��ת������������涨���byte������  
//	               byte b=(byte)token[i];//��ÿ��charת����byte  
//	               bytes[i]=b;//���浽������  
//	           }  
//	           FileOutputStream fos;  
//	        try {  
//	            fos = new FileOutputStream(txt,true);  
//	            fos.write(syn);  
//	            fos.write(bytes);  
//	              
//	               fos.close();  
//	        } catch (Exception e) {  
//	            e.printStackTrace();  
//	        }    
	    }  
	}


