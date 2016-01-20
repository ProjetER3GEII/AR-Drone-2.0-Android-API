package com.example.admin.pilotage;

/**
 *This class build the String AT*PCMD Command to control the drone
 *Syntax AT*REF=[Sequence number],[Flag bit-field],[Roll],[Pitch],[Gaz],[Yaw]
 *@author Mathieu
 *@version 6.0
 */
public class Pilotage {

    enum TypeTrame{DECOLLAGE,PILTOAGE,ATTERISSAGE,URGENCE,HOVERING};
    TypeTrame mTrame;
    int iCompteur;
    String SCMD;
    float fZMPilotHorX;
    float fZMPilotHorY;
    float fZMPilotVertRZ;
    float fZMPilotVertZ;

    /**
     *Command Parameter
     */
    String LeftRight = "0";
    /**
     *Command Parameter
     */
    String FrontBack = "0";
    /**
     *Command Parameter
     */
    String VerticalSpeed = "0";
    /**
     *Command Parameter
     */
    String AngularSpeed = "0";

    /**
     * Motor Constants
     * Px = positive x% power
     * Nx = Negative x% power
     */
    public static final String iP5   = "1028443341"; //+5%
    public static final String iP10  = "1036831949"; //+10%
    public static final String iP20  = "1045220557"; //+20%
    public static final String iP25  = "1048576000"; //+25%
    public static final String iP50  = "1056964608"; //+50%
    public static final String iP75  = "1061158912"; //+75%
    public static final String iP100 = "1065353216"; //+100%
    public static final String iN5   = "-1119040307"; //-5%
    public static final String iN10  = "-1110651699"; //-10%
    public static final String iN20  = "-1102263091"; //-20%
    public static final String iN25  = "-1098907648"; //-25%
    public static final String iN50  = "-1090519040"; //-50%
    public static final String iN75  = "-1086324736"; //-75%
    public static final String iN100 = "-1082130432"; //-100%

    /**
     *Initializes to Zero all parameter: the sequence number, the AT command ...
     */
    public void Pilotage(){
        mTrame=TypeTrame.HOVERING;
        iCompteur=0;
        SCMD="0";
        fZMPilotHorX=0.1f;
        fZMPilotHorY=0.1f;
        fZMPilotVertRZ= 0.1f;
        fZMPilotVertZ=0.1f;
    }
    /**
     *This function increment the sequence number
     */
    private void IncrementeCompteur (){ //incremente le numéro de commande et passe a 1 si on atteind 1000
        if(iCompteur >= 1000){
            iCompteur = 1;
        }else{
            iCompteur++;
        }
    }

    /**
     *You can use this function outside the class for use the sequence number for example Config Use or other ...
     */
    public int RecupSequenceNum (int iIncrementation){ //permet a une classe exterieur de recuperer le numéro de sequence actuel et de liberer les X prochaine sequences pour des utilisations externe
        int iReturn=iCompteur;
        int ibcl=0;
        for(ibcl =0; ibcl<iIncrementation;ibcl++) {
            IncrementeCompteur();
        }
        return iReturn;
    }

    /**
     * This fuction
     * @param iAnimation LED animation numbers: 0: BLINK_GREEN_RED 1: BLING_GREEN 2: BLINK_RED 3: BLINK_ORANGE 4: SNAKE_GREEN_RED 5: FIRE 6: STANDARD 7: RED 8: GREEN 9: RED_SNAKE 10: BLANK 11: RIGHT_MISSILE 12: LEFT_MISSILE 13: DOUBLE_MISSILE 14: FRONT_LEFT_GREEN_OTHERS_RED 15: FRONT_RIGHT_GREEN_OTHERS_RED 16: REAR_LEFT_GREEN_OTHERS_RED 17: REAR_RIGHT_GREEN_OTHERS_RED 18: LEFT_GREEN_RIGHT_RED 19: LEFT_RED_RIGHT_GREEN 20: BLINK_STANDARDs
     * @param iDuration in second and 0 if infinity
     * @return the string Led Command
     */
    public String LedMaxiControl(int iAnimation, int iDuration ){
        String strLED;
        strLED ="AT*LED="+iCompteur+","+iAnimation+",1056964608,"+iDuration;
        IncrementeCompteur();
        return strLED;
    }

    /**
     *This function return the String command to trim the drone and take of
     *@return String AT*FTRIM and AT*REF command
     */
    public String Decollage(){
        String strATCMD ="AT*FTRIM=1\rAT*REF=2,290718208";
        iCompteur=2;
        return strATCMD;
    }

    /**
     *This function return the WatchDog command
     *@return String AT*COMWDG command
     */
    public String WatchDog(){
        String strATCMD="AT*COMWDG=" + iCompteur;
        IncrementeCompteur();
        return strATCMD;
    }

    /**
     *This function return the String command Landing
     *@return String AT*REF Command
     */
    public String Atterrissage(){
        String strATCMD="AT*REF=" + iCompteur + ",290717696";
        iCompteur=0;
        return strATCMD;
    }

    /**
     *This function return the String command to Landing Emergency
     *@return String AT*REF command
     */
    public String Urgence(){
        String strATCMD ="AT*REF=" + iCompteur + ",290717952";
        IncrementeCompteur();
        return strATCMD;
    }

    /**
     * This function return the String command to run annimation
     * @param iAnimation  0: PHI_M30_DEG 1: PHI_30_DEG 2: THETA_M30_DEG 3: THETA_30_DEG 4: THETA_20_DEG_YAW_200_DEG 5: THETA_20_DEG_YAW_M200_DEG 6: TURNAROUND 7: TURNAROUND_GODOWN 8: YAW_SHAKE 9: YAW_DANCE 10: PHI_DANCE 11: THETA_DANCE 12: VZ_DANCE 13: WAVE 14: PHI_THETA_MIXED 15: DOUBLE_PHI_THETA_MIXED 16: FLIP_AHEAD 17: FLIP_BEHIND 18: FLIP_LEFT 19: FLIP_RIGHT
     * @param iDuration 0 = infinity
     * @return String AT*config command
     */
    public String DroneAnimation(int iAnimation, int iDuration){
        String strAnnim;
        strAnnim ="AT*ANIM="+iCompteur+",\"control:flight_anim\","+iAnimation+","+iDuration;
        IncrementeCompteur();
        return strAnnim;
    }

    /**
     *This function return the String command to move the drone
     *@return String AT*PCMD command
     */
    public String BuildCMD (boolean bFlag){
        String strFlag;
        if(bFlag==true){
            strFlag="1";
        }
        else{
            strFlag="0";
        }
        switch (mTrame){
            case PILTOAGE:
                SCMD="AT*PCMD="+iCompteur+","+strFlag+","+LeftRight+","+FrontBack+","+VerticalSpeed+","+AngularSpeed;
                break;
            case HOVERING:
                LeftRight="0";
                FrontBack = "0";
                VerticalSpeed="0";
                AngularSpeed="0";
                SCMD="AT*PCMD="+iCompteur+","+strFlag+","+LeftRight+","+FrontBack+","+VerticalSpeed+","+AngularSpeed;
                break;
        }
        IncrementeCompteur();
        return SCMD;
    }

    /**
     *Use this function if you want reset the flight
     */
    public void Hovering(){
        mTrame=TypeTrame.HOVERING;
    }

    /**
     *Use this function to move the drone
     *enter the Pitch and the roll
     *float [-10;+10]
     */
    public void PilotageHorizontal(float MoveY, float MoveX){
        mTrame = TypeTrame.PILTOAGE;
        if(MoveX >= -10.0f && MoveX <= -8.75f){
            FrontBack=iP100;
        }
        else if(MoveX > -8.75f && MoveX <= -6.25f){
            FrontBack=iP75;
        }
        else if(MoveX > -6.25f && MoveX <= -3.75f){
            FrontBack=iP50;
        }
        else if(MoveX > -3.75f && MoveX <= -2.25f){
            FrontBack=iP25;
        }
        else if(MoveX > -2.25f && MoveX <= -1.5f){
            FrontBack=iP20;
        }
        else if(MoveX > -1.5f && MoveX <= -0.75f){
            FrontBack=iP10;
        }
        else if(MoveX > -0.75f && MoveX <-0.1f){
            FrontBack=iP5;
        }
        else if(MoveX > -0.1f && MoveX <0.1f){
            FrontBack="0";
        }
        else if(MoveX > 0.1f && MoveX <= 0.75f){
            FrontBack=iN5;
        }
        else if(MoveX > 0.75f && MoveX <= 1.5f){
            FrontBack =iN10;
        }
        else if(MoveX > 1.5f && MoveX <= 2.25f){
            FrontBack =iN20;
        }
        else if(MoveX < 2.25f && MoveX <= 3.75f){
            FrontBack =iN25;
        }
        else if(MoveX > 3.75f && MoveX <= 6.25f){
            FrontBack =iN50;
        }
        else if(MoveX > 6.25f && MoveX <= 8.75f){
            FrontBack =iN75;
        }
        else if(MoveX > 8.75f && MoveX <= 10.0f){
            FrontBack =iN100;
        }
        else{
            FrontBack="0";
        }
        if ((MoveX > -fZMPilotHorX) && (MoveX < fZMPilotHorX)){
            FrontBack="0";
        }
        if(MoveY >= -10.0f && MoveY <= -8.75f){
            LeftRight=iN100;
        }
        else if(MoveY > -8.75f && MoveY <= -6.25f){
            LeftRight=iN75;
        }
        else if(MoveY > -6.25f && MoveY <= -3.75f){
            LeftRight=iN50;
        }
        else if(MoveY > -3.75f && MoveY <= -2.25f){
            LeftRight=iN25;
        }
        else if(MoveY > -2.25f && MoveY <= -1.5f){
            LeftRight=iN20;
        }
        else if(MoveY > -1.5f && MoveY <= -0.75f){
            LeftRight=iN10;
        }
        else if(MoveY > -0.75f && MoveY <-0.1){
            LeftRight=iN5;
        }
        else if(MoveY > -0.1f && MoveY <0.1f){
            LeftRight="0";
        }
        else if(MoveY > 0.1f && MoveY <= 0.75f){
            LeftRight=iP5;
        }
        else if(MoveY > 0.75f && MoveY <= 1.5f){
            LeftRight =iP10;
        }
        else if(MoveY > 1.5f && MoveY <= 2.25f){
            LeftRight =iP20;
        }
        else if(MoveY < 2.25f && MoveY <= 3.75f){
            LeftRight =iP25;
        }
        else if(MoveY > 3.75f && MoveY <= 6.25f){
            LeftRight =iP50;
        }
        else if(MoveY > 6.25f && MoveY <= 8.75f){
            LeftRight =iP75;
        }
        else if(MoveY > 8.75f && MoveY <= 10.0f){
            LeftRight =iP100;
        }
        else {
            LeftRight ="0";
        }
        if((MoveY > -fZMPilotHorY) && (MoveY <fZMPilotHorY)){
            LeftRight="0";
        }
    }

    /**
     *Use this function to move the drone
     *enter the Yaw and the vertical speed
     *float [-10;+10]
     */
    public void PilotageVertical(float RotZ, float MoveZ){
        mTrame = TypeTrame.PILTOAGE;
        if(RotZ >= -10.0f && RotZ <= -8.75f){
            AngularSpeed=iN100;
        }
        else if(RotZ > -8.75f && RotZ <= -6.25f){
            AngularSpeed=iN75;
        }
        else if(RotZ > -6.25f && RotZ <= -3.75f){
            AngularSpeed=iN50;
        }
        else if(RotZ > -3.75f && RotZ <= -2.25f){
            AngularSpeed=iN25;
        }
        else if(RotZ > -2.25f && RotZ <= -1.5f){
            AngularSpeed=iN20;
        }
        else if(RotZ > -1.5f && RotZ <= -0.75f){
            AngularSpeed=iN10;
        }
        else if(RotZ > -0.75f && RotZ <-0.1f){
            AngularSpeed=iN5;
        }
        else if(RotZ > -0.1f && RotZ <0.1f){
            AngularSpeed="0";
        }
        else if(RotZ > 0.1 && RotZ <= 0.75f){
            AngularSpeed=iP5;
        }
        else if(RotZ > 0.75f && RotZ <= 1.5f){
            AngularSpeed =iP10;
        }
        else if(RotZ > 1.5f && RotZ <= 2.25f){
            AngularSpeed =iP20;
        }
        else if(RotZ < 2.25f && RotZ <= 3.75f){
            AngularSpeed =iP25;
        }
        else if(RotZ > 3.75f && RotZ <= 6.25f){
            AngularSpeed =iP50;
        }
        else if(RotZ > 6.25f && RotZ <= 8.75f){
            AngularSpeed =iP75;
        }
        else if(RotZ > 8.75f && RotZ <= 10.0f){
            AngularSpeed =iP100;
        }
        else{
            AngularSpeed = "0";
        }
        if((RotZ > -fZMPilotVertRZ) && (RotZ <fZMPilotVertRZ)){
            AngularSpeed="0";
        }
        if(MoveZ >= -10.0f && MoveZ <= -8.75f){
            VerticalSpeed=iN100;
        }
        else if(MoveZ > -8.75f && MoveZ <= -6.25f){
            VerticalSpeed=iN75;
        }
        else if(MoveZ > -6.25f && MoveZ <= -3.75f){
            VerticalSpeed=iN50;
        }
        else if(MoveZ > -3.75f && MoveZ <= -2.25f){
            VerticalSpeed=iN25;
        }
        else if(MoveZ > -2.25f && MoveZ <= -1.5f){
            VerticalSpeed=iN20;
        }
        else if(MoveZ > -1.5f && MoveZ <= -0.75f){
            VerticalSpeed=iN10;
        }
        else if(MoveZ > -0.75f && MoveZ <-0.1f){
            VerticalSpeed=iN5;
        }
        else if(MoveZ > -0.1f && MoveZ <0.1f){
            VerticalSpeed="0";
        }
        else if(MoveZ > 0.1f && MoveZ <= 0.75f){
            VerticalSpeed=iP5;
        }
        else if(MoveZ > 0.75f && MoveZ <= 1.5f){
            VerticalSpeed =iP10;
        }
        else if(MoveZ > 1.5f && MoveZ <= 2.25f){
            VerticalSpeed =iP20;
        }
        else if(MoveZ < 2.25f && MoveZ <= 3.75f){
            VerticalSpeed =iP25;
        }
        else if(MoveZ > 3.75f && MoveZ <= 6.25f){
            VerticalSpeed =iP50;
        }
        else if(MoveZ > 6.25f && MoveZ <= 8.75f){
            VerticalSpeed =iP75;
        }
        else if(MoveZ > 8.75f && MoveZ <= 10.0f){
            VerticalSpeed =iP100;
        }
        else{
            VerticalSpeed="0";
        }
        if(MoveZ > -fZMPilotVertZ && MoveZ <fZMPilotVertZ){
            VerticalSpeed="0";
        }
    }

    /*function to change the "zero" zone */
    public float getfZMPilotHorX() {
        return fZMPilotHorX;
    }

    public float getfZMPilotHorY() {
        return fZMPilotHorY;
    }

    public float getfZMPilotVertRZ() {
        return fZMPilotVertRZ;
    }

    public float getfZMPilotVertZ() {
        return fZMPilotVertZ;
    }

    public boolean setfZMPilotHorX(float fZMPilotHorX) {
        try {
            this.fZMPilotHorX = fZMPilotHorX;
            return true;
        } catch (NumberFormatException e){
            // On ne change pas la valeur si problèmes
            return false;
        }

    }

    public boolean setfZMPilotHorY(float fZMPilotHorY) {
        try{
            this.fZMPilotHorY = fZMPilotHorY;
            return true;
        } catch (NumberFormatException e) {
            // On ne change pas la valeur si problèmes
            return false;
        }
    }

    public boolean setfZMPilotVertRZ(float fZMPilotVertRZ) {
        try {
            this.fZMPilotVertRZ = fZMPilotVertRZ;
            return true;
        }catch (NumberFormatException e) {
            // On ne change pas la valeur si problèmes
            return false;
        }
    }

    public boolean setfZMPilotVertZ(float fZMPilotVertZ) {
        try {
            this.fZMPilotVertZ = fZMPilotVertZ;
            return true;
        }catch (NumberFormatException e) {
            // On ne change pas la valeur si problèmes
            return false;
        }
    }
}
