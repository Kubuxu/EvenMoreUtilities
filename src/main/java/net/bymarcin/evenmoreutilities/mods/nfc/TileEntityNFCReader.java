package net.bymarcin.evenmoreutilities.mods.nfc;

import li.cil.oc.api.Network;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.TileEntityEnvironment;


public class TileEntityNFCReader extends TileEntityEnvironment {

    public TileEntityNFCReader() {
    	 node = Network.newNode(this, Visibility.Network).create();
    }
    
    public void sendEvent(String sennder, String data){
    	if(node!=null){
    		node.sendToReachable("computer.signal","nfc_data",sennder, data);
    	}
    }

	
}
