
package prims;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class MinSpanTree{
    int parent,weight;
}

public class Prims {

    int vertices,maxedge,count=0;
    int[][] adj;
    int[] weight;
    boolean[] visited;
    
    int findmin(){
        int weightvalue=1000;
        int vertexvalue=0;
        for(int i=0;i<vertices;i++){
            if(!visited[i] && weight[i]<weightvalue){
                weightvalue=weight[i];
                vertexvalue=i;
            }
        }
        return vertexvalue;
    }
    
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        Scanner kbd=new Scanner(System.in);
        Prims pr=new Prims();
        int temp1,temp2,temp3,tempmin=0,c,currentVertex=0,destVertex=0,tempcount=0,row=0,column=0,tempIndex=0,tempVar=0;
        FileReader inputStream = null;
        FileWriter outputStream = null;
        BufferedWriter bw=null;
        int[][] adjTemp;
        int[][] mstadj;
        int tempItr=0;
        char check='a';
        boolean flag=true;
        int[][] outputArray;
        pr.count=0;
        
        System.out.print("Enter the number of vertices : ");
        pr.vertices=kbd.nextInt();
        
        pr.maxedge=pr.vertices*(pr.vertices-1);
        pr.adj=new int[pr.vertices][pr.vertices];
        adjTemp=new int[pr.maxedge][3];
        outputArray=new int[pr.vertices-1][3];
        
        MinSpanTree[] msp=new MinSpanTree[pr.vertices];
        
        for(int i=0;i<pr.vertices;i++)
            for(int j=0;j<pr.vertices;j++)
                pr.adj[i][j]=0;
        
        /*for(int i=0;i<pr.maxedge;i++){
            System.out.print("Enter edge-"+(i+1)+"'s vertices (<-1,-1> to break): ");
            temp1=kbd.nextInt();
            temp2=kbd.nextInt();
            if(temp1==-1 && temp2==-1)
                break;
            System.out.print("Weight : ");
            temp3=kbd.nextInt();
            pr.adj[temp1][temp2]=temp3;
            pr.adj[temp2][temp1]=temp3;
        }*/
        
        try{
            inputStream = new FileReader("graph3.txt");
            while ((c = inputStream.read()) != -1) {
                
                while(flag==true && c!=-1){
                    if(tempcount==0){
                        currentVertex=c-'0';
                        //System.out.println("Iteration"+pr.count+", currentVertex value : "+currentVertex);
                        adjTemp[pr.count][0]=currentVertex;
                        c = inputStream.read();
                        c = inputStream.read();
                        c = inputStream.read();
                        destVertex=c-'0';
                        adjTemp[pr.count][1]=destVertex;
                        //System.out.println("Iteration"+pr.count+", desVertex value : "+destVertex);
                        c = inputStream.read();
                        c = inputStream.read();
                        adjTemp[pr.count][2]=c-'0';
                        //pr.adj[currentVertex][destVertex]=c-'0';
                        //pr.adj[destVertex][currentVertex]=c-'0';
                        c = inputStream.read();
                        c = inputStream.read();
                        c = inputStream.read();
                        check=(char)c;
                        pr.count++;
                    }

                    if(check=='('){
                        //c = inputStream.read();
                        c = inputStream.read();
                        destVertex=c-'0';
                        adjTemp[pr.count][0]=currentVertex;
                        adjTemp[pr.count][1]=destVertex;
                        //System.out.println("Iteration"+pr.count+" currentVertex value : "+currentVertex);
                        //System.out.println("Iteration"+pr.count+" destVertex value : "+destVertex);
                        c = inputStream.read();
                        c = inputStream.read();
                        adjTemp[pr.count][2]=c-'0';
                        //pr.adj[currentVertex][destVertex]=c-'0';
                        //pr.adj[destVertex][currentVertex]=c-'0';
                        c = inputStream.read();
                        c = inputStream.read();
                        c = inputStream.read();
                        check=(char)c;
                        pr.count++;
                        tempcount++;
                    }

                    if(check==','){
                        flag=false;
                    }
                    
                    
                }
                tempcount=0;
                c = inputStream.read();
                c=inputStream.read();
                flag=true;
            }
            
        }
        catch(IOException e){
            System.out.println("Some error");
        }
        
        
        /*System.out.println("\n\nTemporary adjacency\n\n");
        for(int i=0;i<pr.count;i++){
            for(int j=0;j<3;j++){
                System.out.print(adjTemp[i][j]+"    ");
            }
            System.out.println();
        }*/
        
        
        for(int i=0;i<pr.count;i++){
            row=adjTemp[i][0];
            column=adjTemp[i][1];
            pr.adj[row][column]=adjTemp[i][2];
            pr.adj[column][row]=adjTemp[i][2];
        }
        
        /*System.out.println("\nMy adjacency matrix\n");
        for(int i=0;i<pr.vertices;i++){
            for(int j=0;j<pr.vertices;j++){
                System.out.print(pr.adj[i][j]+"\t");
            }
            System.out.println();
        }*/
        
        
        pr.weight=new int[pr.vertices];
        pr.visited=new boolean[pr.vertices];
        for(int i=0;i<pr.vertices;i++){
            pr.weight[i]=1000;
            pr.visited[i]=false;
            msp[i]=new MinSpanTree();
        }
        
        for(int x=0;x<pr.vertices-1;x++){
            tempmin=pr.findmin();
            pr.visited[tempmin]=true;
            for(int j=0;j<pr.vertices;j++){
                if(pr.adj[tempmin][j]!=0 && !pr.visited[j] && pr.adj[tempmin][j]<pr.weight[j]){
                    pr.weight[j]=pr.adj[tempmin][j];
                    msp[j].parent=tempmin;
                }
            }
        }
        
        System.out.println("The edges in the MST are : \n");
        System.out.println("From    To  Weight");
        for(int i=1;i<pr.vertices;i++)
            System.out.println(msp[i].parent+"----->"+i+"      "+pr.weight[i]);
        
        System.out.println("\n\nAn output text file has been generated too!");
        
        for(int i=0;i<pr.vertices-1;i++){
            outputArray[i][0]=msp[i+1].parent;
            outputArray[i][1]=i+1;
            outputArray[i][2]=pr.weight[i+1];
        }
        
        /*System.out.println("\n\nPrinting outputarray\n");
        for(int i=0;i<pr.vertices-1;i++){
            System.out.println(outputArray[i][0]+"  "+outputArray[i][1]+"   "+outputArray[i][2]);
        }*/
        
        mstadj=new int[(pr.vertices-1)*2][3];
        for(int itr=0;itr<pr.vertices-1;itr++){
            mstadj[tempItr][0]=outputArray[itr][0];
            mstadj[tempItr][1]=outputArray[itr][1];
            mstadj[tempItr][2]=outputArray[itr][2];
            ++tempItr;
            mstadj[tempItr][0]=outputArray[itr][1];
            mstadj[tempItr][1]=outputArray[itr][0];
            mstadj[tempItr][2]=outputArray[itr][2];
            ++tempItr;
        }
        
        for(int i=0;i<tempItr;i++){
            for(int j=i+1;j<tempItr;j++){
                if(mstadj[i][0]>mstadj[j][0]){
                    tempVar=mstadj[i][0];
                    mstadj[i][0]=mstadj[j][0];
                    mstadj[j][0]=tempVar;
                    
                    tempVar=mstadj[i][1];
                    mstadj[i][1]=mstadj[j][1];
                    mstadj[j][1]=tempVar;
                    
                    tempVar=mstadj[i][2];
                    mstadj[i][2]=mstadj[j][2];
                    mstadj[j][2]=tempVar;
                }
            }
        }
        
        try{
            //outputStream=new FileWriter("outputMST.txt",true);
            PrintWriter writer=new PrintWriter("outputMST.txt","UTF-8");
            temp1=mstadj[0][0];
            temp2=mstadj[0][1];
            temp3=mstadj[0][2];
            writer.print(temp1+"(("+temp2+","+temp3+")");
            for(int i=1;i<tempItr;i++){
                if(temp1==mstadj[i][0]){
                    writer.print(",("+mstadj[i][1]+","+mstadj[i][2]+")");
                }
                else{
                    temp1=mstadj[i][0];
                    writer.print("),");
                    writer.println();
                    writer.print(temp1+"(("+mstadj[i][1]+","+mstadj[i][2]+")");
                }
                
                if(i==tempItr-1)
                    writer.print(")");
            }
            writer.close();
        }
        
        catch(IOException e){
            
        }
        finally{
            try{
                if(inputStream!=null) inputStream.close();
                if(outputStream!=null) outputStream.close();
                //if(bw!=null) bw.close();
            }
            catch(IOException ex){
            }
        }
    }
    
}
