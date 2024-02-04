package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.InstrumentDao;
import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.InstrumentModel;
import com.covideinfo.model.InstrumentType;

@SuppressWarnings("unchecked")
@Repository("instrumentDao")
public class InstrumentDaoImpl extends AbstractDao<Long, InstrumentModel> implements InstrumentDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<InstrumentModel> getInstrumentModelList() {
		 List<InstrumentModel> list=null;
		 list=getSession().createCriteria(InstrumentModel.class).list();
				 
		return list;
	}

	
	@Override
	public List<InstrumentType> getInstrumentTypeList() {
		 List<InstrumentType> list=null;
		 list=getSession().createCriteria(InstrumentType.class).list();
				 
		return list;
	}

	@Override
	public boolean saveInstrumentForm(Instrument instrume) {
		boolean flag=false;
		try {
			getSession().save(instrume);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public Instrument getInstrumentNoCheckExitOrNot(String value) {
		Instrument pojo=null;
		pojo=(Instrument) getSession().createCriteria(Instrument.class)
				.add(Restrictions.eq("instrumentNo", value)).uniqueResult();
		return pojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Instrument> getInstrumentForDeepFrsszerList(List<Long> ids) {
		
		List<Long> ids1=new ArrayList<>();
		List<Long> idsFinal=new ArrayList<>();
		
		ids1=getSession().createCriteria(Deepfreezer.class)
				.setProjection(Projections.property("instrument.id")).list();
		for (Long chekid : ids) {
			 if (!ids1.contains(chekid)) {
				 idsFinal.add(chekid);
			}
		}
		List<Instrument> def=new ArrayList<>();
		try {
			if(idsFinal.size()>0) {
				def=getSession().createCriteria(Instrument.class)
				.add(Restrictions.in("instrumentType.id",idsFinal)).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getInstrumentTypeForDeepfrezer(String deepf) {
		List<Long> ids=null;
		ids=getSession().createCriteria(InstrumentType.class)
				.add(Restrictions.eq("instrumentCode",deepf))
				.setProjection(Projections.property("id")).list();
		return ids;
	}

	@Override
	public Instrument getInstrumentWithId(Long instrument) {
		Instrument ins=null;
		ins=(Instrument) getSession().createCriteria(Instrument.class)
				.add(Restrictions.eq("id",instrument)).uniqueResult();
		return ins;
	}

	@Override
	public boolean saveDeepfreserForm(List<Deepfreezer> defList) {
		boolean flag=false;
		try {
			for(Deepfreezer add:defList) {
				getSession().save(add);
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public List<Deepfreezer> instrumentShelfs(Long insturmentId) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(Deepfreezer.class)
				.add(Restrictions.eq("instrument.id", insturmentId)).list();
	}


	@Override
	public Deepfreezer sehelfDetails(Long shelfId) {
		// TODO Auto-generated method stub
		return (Deepfreezer) getSession().get(Deepfreezer.class, shelfId);
	}


	@Override
	public boolean saveDeepfreezerShelf(List<DeepfreezerShelf> defList) {
		boolean flag=false;
		try {
			for(DeepfreezerShelf add:defList) {
				getSession().save(add);
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
	
	

}
