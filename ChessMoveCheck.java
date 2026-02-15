public class ChessMoveCheck {

    public static boolean checkMove(String typ,String farbe,int sx,int sy,int zx,int zy,String[][] brett){

        int dx=zx-sx;
        int dy=zy-sy;
        int absDx=Math.abs(dx);
        int absDy=Math.abs(dy);

        switch(typ){
            case "Bauer":
                if(farbe.equals("weiss")){
                    if(dx==0 && dy==-1 && brett[zx][zy].equals("")) return true;
                    if(absDx==1 && dy==-1 && !brett[zx][zy].equals("") && !brett[zx][zy].startsWith(farbe)) return true;
                } else{
                    if(dx==0 && dy==1 && brett[zx][zy].equals("")) return true;
                    if(absDx==1 && dy==1 && !brett[zx][zy].equals("") && !brett[zx][zy].startsWith(farbe)) return true;
                }
                return false;

            case "Turm": if(dx!=0 && dy!=0) return false; return istPfadFrei(sx,sy,zx,zy,brett);
            case "Laeufer": if(absDx!=absDy) return false; return istPfadFrei(sx,sy,zx,zy,brett);
            case "Dame": if(dx==0||dy==0||absDx==absDy) return istPfadFrei(sx,sy,zx,zy,brett); return false;
            case "Springer": return (absDx==2 && absDy==1)||(absDx==1 && absDy==2);
            case "Koenig": return absDx<=1 && absDy<=1;
            default: return false;
        }
    }

    private static boolean istPfadFrei(int sx,int sy,int zx,int zy,String[][] brett){
        int dx=Integer.signum(zx-sx);
        int dy=Integer.signum(zy-sy);

        int x=sx+dx;
        int y=sy+dy;

        while(x!=zx || y!=zy){
            if(!brett[x][y].equals("")) return false;
            x+=dx;
            y+=dy;
        }
        return true;
    }
}
