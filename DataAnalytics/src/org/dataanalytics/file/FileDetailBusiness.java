package org.dataanalytics.file;

import org.dataanalytics.utils.Utils;
import java.util.concurrent.locks.ReentrantLock;;

public class FileDetailBusiness {

	public long insert(long clientID , String fileName , String data)
	{
		try
		{
			data= Utils.csvToJsonString(data);
			FileDetailDO fileDetailDO = new FileDetailDO();
			fileDetailDO.setClientID(clientID);
			fileDetailDO.setFileName(fileName);
			fileDetailDO.setData(data);
			fileDetailDO.setTimeCreated(Utils.getCurrentTime());
			fileDetailDO.setStatus(FileDetailStatusEnum.ACTIVE.getType());
			fileDetailDO.setPublic(false);
			long insertedID = FileDetailDCL.getFileDetailDCL().insert(fileDetailDO);
			System.out.println("file inserted id :" + insertedID);
			return insertedID;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}


}
