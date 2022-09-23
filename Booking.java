import java.util.*;
public class Booking
{
/* floor no. 1,2,3... of req room, 
   occupancy : max allowed persons in the room 1,2 ..,
   AC : Is AC req? True or False*/
    byte floor,occupancy;boolean AC;
    public Booking(byte floor, byte occupancy, boolean AC)
    {
        this.floor=floor;
        this.occupancy=occupancy;
        this.AC=AC;
    }
    static ArrayList<Integer> taken= new ArrayList<Integer>();
/* taken would maintain the already occupied room_ids 
these are static so that all objects may access same value as set in main.*/
    public static void main(String args[])
    {
        Scanner in=new Scanner(System.in);
        // users can query for booking infinite times unless they say false.
        for(;;) 
        {
          System.out.println("Ready to continue booking? true/false"); 
          boolean f=in.nextBoolean();
          if(f==false)
          break;
            System.out.println("Max persons in the room?");
            byte occupancy=in.nextByte();
            System.out.println("AC is req. true/false?");
            boolean AC=in.nextBoolean();
            System.out.println("Which floor req? Provide other value than 1 or 2 for no floor preference");
            byte floor=in.nextByte();
            Booking b=new Booking(floor,occupancy,AC);
            int room_id=(b.gen_room_id())[0];
            int floor_no=((b.gen_room_id())[1]>2)?2:1;
            if (room_id!=-1)
            {
                taken.add(room_id); // keep list of booked room to check letter
                String ac=(AC==true)?"Yes":"No";
                System.out.println("Wishing a very happy stay! Room number:"+room_id+" floor:"+floor_no+" AC?"+ac+" Occupancy:"+occupancy+" Estimated Cost/day :Rs"+b.price());
            }
            else
                System.out.println("Not available! Retry.");
        }
    }
    private int[] gen_room_id()
    {
/* occupancy=1 or 2 or .., AC=1 or non-AC=0
    101 - Single Occupancy, AC
    102 - Double Occupancy, AC
    103 - Double Occupancy
    201 - Single Occupancy, AC
    202 - Single Occupancy
    203 - Double Occupancy, AC
    204 - Triple Occupancy, AC
*/
int room_no=-1;
int acf=(AC==true)?1:0; 
int rooms[]={101,102,103,201,202,203,204}; //room ids
int max[]={1,2,2,1,1,2,3}; // max allowed occupancy 
int fac[]={1,1,0,1,0,1,1}; // AC 1=availability 
int start=(floor==2)?3:0; // start index=0 for floor=1 , 3 for floor=2 ; else start from 0 only.
int end=(floor==1)?3:7;
int pos=-1;
boolean f=false;
for (int i=start;i<end; i++)
{
    if (max[i]==occupancy && fac[i]==acf)
    {
        if (taken.contains(rooms[i])) // if already taken check availability among next rooms.
        continue;
        room_no=rooms[i];
        pos=i;
    }

}
int[] room={room_no,pos}; 
return room;
    }
    private int price()
    {
        int sum=(occupancy==1)?2000:((occupancy==2)?3000:4000);//for max allowed persons.
        sum=sum+((AC==true)?1000:0);//additional for AC.
        return sum;
    }
}