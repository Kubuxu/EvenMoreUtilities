package net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity;

import cofh.api.energy.IEnergyHandler;
import cofh.util.BlockHelper;
import cofh.util.EnergyHelper;
import cofh.util.ServerHelper;
import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.MultiblockValidationException;
import erogenousbeef.core.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public class TileEntityPowerTap extends RectangularMultiblockTileEntityBase implements IEnergyHandler{
	
	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Power tap may not be placed in the battery's frame", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));	
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Power tap may not be placed in the battery's sides", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		TileEntity entityBelow = this.worldObj.getBlockTileEntity(this.xCoord,  this.yCoord - 1, this.zCoord);
		if ((entityBelow instanceof TileEntityElectrode)) {
			return;
		}
		throw new MultiblockValidationException(String.format("%d, %d, %d - Power tap must be placed on electrode", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));
		
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Power tap may not be placed in the battery's bottom", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));
	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Power tap may not be placed in the battery's interior", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));	
	}

	@Override
	public void onMachineActivated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMachineDeactivated() {
		// TODO Auto-generated method stub
		
	}
	
	public void onTransferEnergy(){
			if(ServerHelper.isClientWorld(worldObj) || isOutput() || getMultiblockController()==null) return;
			TileEntity tile = BlockHelper.getAdjacentTileEntity(this, ForgeDirection.UP);
			int energyGet=0;
			if (EnergyHelper.isEnergyHandlerFromSide(tile,ForgeDirection.VALID_DIRECTIONS[(1 ^ 0x1)])){
				energyGet = ((IEnergyHandler)tile).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[(1 ^ 0x1)], Math.min(10000, ((BigBattery)getMultiblockController()).getStorage().getEnergyStored()), false); 
			}  
			((BigBattery)getMultiblockController()).getStorage().modifyEnergyStored(-energyGet);
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new BigBattery(this.worldObj);
	}

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return BigBattery.class;
	}
	
	/* IEnergyHandler */
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if(getMultiblockController()!=null && isOutput()){
			return ((BigBattery)getMultiblockController()).getStorage().receiveEnergy(maxReceive, simulate);
		}
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(getMultiblockController()!=null && !isOutput()){
			return ((BigBattery)getMultiblockController()).getStorage().extractEnergy(maxExtract, simulate);
		}
		return 0;
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		if(from == ForgeDirection.UP)
			return true;
		return false;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if(getMultiblockController()!=null && from == ForgeDirection.UP){
			return ((BigBattery)getMultiblockController()).getStorage().getEnergyStored();
		}
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if(getMultiblockController()!=null && from == ForgeDirection.UP){
			return ((BigBattery)getMultiblockController()).getStorage().getMaxEnergyStored();
		}
		return 0;
	}
	
	public boolean isOutput() {
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord)==0;
	}

}