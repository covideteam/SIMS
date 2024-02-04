package com.covideinfo.eform.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.eform.dao.EformDao;
import com.covideinfo.eform.model.EForm;
import com.covideinfo.eform.model.EFormEleCaliculation;
import com.covideinfo.eform.model.EFormGroup;
import com.covideinfo.eform.model.EFormGroupElement;
import com.covideinfo.eform.model.EFormGroupElementValue;
import com.covideinfo.eform.model.EFormMapplingTable;
import com.covideinfo.eform.model.EFormMapplingTableColumn;
import com.covideinfo.eform.model.EFormMapplingTableColumnMap;
import com.covideinfo.eform.model.EFormRule;
import com.covideinfo.eform.model.EFormRuleWithOther;
import com.covideinfo.eform.model.EFormSection;
import com.covideinfo.eform.model.EFormSectionElement;
import com.covideinfo.eform.model.EFormSectionElementValue;
import com.covideinfo.eform.model.EFormVisibility;
import com.covideinfo.model.UserMaster;

@Repository("eformDao")
public class EformDaoImpl extends AbstractDao<Long,EForm> implements EformDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<EForm> findAllEForm() {
		List<EForm> ml = getSession().createCriteria(EForm.class).add(Restrictions.isNull("study")).list();
		return ml;
	}

	@Override
	public EForm checkEForm(String name) {
		return (EForm) getSession().createCriteria(EForm.class).add(Restrictions.eq("name", name))
				.add(Restrictions.isNull("study")).add(Restrictions.eq("active", true)).uniqueResult();
	}

	@Override
	public void saveEForm(EForm crf) {
		getSession().save(crf);
		for (EFormSection sec : crf.getSections()) {
			System.out.println(sec.getName());
			getSession().save(sec);
			for (EFormSectionElement ele : sec.getElement()) {
				ele.setSection(sec);
				getSession().save(ele);
				List<EFormSectionElementValue> elevl = ele.getElementValues();
				if (elevl != null)
					for (EFormSectionElementValue elev : elevl) {
						getSession().save(elev);
					}
			}
			EFormGroup group = sec.getGroup();
			if (group != null) {
				for (EFormGroupElement ele : group.getElement()) {
					ele.setGroup(group);
					getSession().save(ele);
					List<EFormGroupElementValue> elementValues = ele.getElementValues();
					if (elementValues != null)
						for (EFormGroupElementValue elev : elementValues) {
							getSession().save(elev);
						}
				}
				sec.setGroup(group);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EFormVisibility> findAllEFormVisibility() {
		return getSession().createCriteria(EFormVisibility.class).list();
	}

	@Override
	public EForm eformForView(Long eformId) {
		EForm crf = (EForm) getSession().get(EForm.class, eformId);
		Hibernate.initialize(crf.getSections());
		Collections.sort(crf.getSections());
		for (EFormSection sec : crf.getSections()) {
			Hibernate.initialize(sec.getElement());
			if (sec.getElement() == null || sec.getElement().size() == 0) {
			} else {
				for (EFormSectionElement ele : sec.getElement()) {
					if (ele.getType().equals("selectTable")) {
						List<EFormSectionElementValue> evalues = new ArrayList<>();
						EFormMapplingTableColumnMap colMap = eformMapplingTableColumnMap(ele);
						if (colMap != null) {
							String sql = "Select " + colMap.getMappingColumn().getCloumnName() + " from "
									+ colMap.getMappingTable().getTableName();
							SQLQuery query = getSession().createSQLQuery(sql);
							query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
							List results = query.list();
							for (Object object : results) {
								Map<String, String> row = (Map<String, String>) object;
								for (Map.Entry<String, String> m : row.entrySet()) {
									EFormSectionElementValue mv = new EFormSectionElementValue();
									mv.setValue(m.getValue());
									evalues.add(mv);
								}
							}
						}
						ele.setElementValues(evalues);
					} else
						Hibernate.initialize(ele.getElementValues());
				}
			}
			Hibernate.initialize(sec.getGroup());
			EFormGroup group = sec.getGroup();
			if (group != null) {
				Hibernate.initialize(group.getElement());
				if (group.getElement() == null || group.getElement().size() == 0) {
				} else {
					for (EFormGroupElement ele : group.getElement()) {
						Hibernate.initialize(ele.getElementValues());
					}
				}
			}
		}

		return crf;
	}

	private EFormMapplingTableColumnMap eformMapplingTableColumnMap(EFormSectionElement ele) {
		EFormMapplingTableColumnMap map = (EFormMapplingTableColumnMap) getSession()
				.createCriteria(EFormMapplingTableColumnMap.class).add(Restrictions.eq("element", ele)).uniqueResult();
		return map;
	}

	@Override
	public List<EFormSectionElement> sectionElemets(Long id) {
		List<EFormSectionElement> list = new ArrayList<>();
		EForm crf = (EForm) getSession().get(EForm.class, id);
		Hibernate.initialize(crf.getSections());
		for (EFormSection sec : crf.getSections()) {
			Hibernate.initialize(sec.getElement());
			list.addAll(sec.getElement());
		}
		return list;
	}

	@Override
	public List<EFormGroupElement> groupElemets(Long id) {
		List<EFormGroupElement> list = new ArrayList<EFormGroupElement>();
		EForm crf = (EForm) getSession().get(EForm.class, id);
		Hibernate.initialize(crf.getSections());
		for (EFormSection sec : crf.getSections()) {
			Hibernate.initialize(sec.getGroup());
		    EFormGroup group = sec.getGroup();
			if (group != null) {
				Hibernate.initialize(group.getElement());
				if (group.getElement() != null || group.getElement().size() > 0) {
					list.addAll(group.getElement());
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EFormSection> sections(Long id) {
		return getSession().createCriteria(EFormSection.class).add(Restrictions.eq("eform.id", id)).list();
	}

	@Override
	public List<EFormGroup> groups(Long id) {
		List<EFormSection> sections = sections(id);
		List<EFormGroup> groups = new ArrayList<>();
		for (EFormSection s : sections) {
			if (s.getGroup() != null)
				groups.add(s.getGroup());
		}
		return groups;
	}

	@Override
	public EForm getEForm(Long crfid) {
		return (EForm) getSession().createCriteria(EForm.class)
				.add(Restrictions.eq("id", crfid)).uniqueResult();
	}

	@Override
	public EFormSectionElement sectionElement(Long secId) {
		return (EFormSectionElement) getSession().createCriteria(EFormSectionElement.class)
				.add(Restrictions.eq("id", secId)).uniqueResult();
	}

	@Override
	public EFormGroupElement groupElement(Long groupId) {
		return (EFormGroupElement) getSession().createCriteria(EFormGroupElement.class)
				.add(Restrictions.eq("id", groupId)).uniqueResult();
	}

	@Override
	public EFormSection section(Long secIdAction) {
		return (EFormSection) getSession().createCriteria(EFormSection.class)
				.add(Restrictions.eq("id", secIdAction)).uniqueResult();
	}

	@Override
	public EFormGroup group(Long groupIdAction) {
		return (EFormGroup) getSession().createCriteria(EFormGroup.class)
				.add(Restrictions.eq("id", groupIdAction)).uniqueResult();
	}

	@Override
	public void saveEFormVisibility(EFormVisibility visibilty) {
		getSession().save(visibilty);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EForm> findAllCrfsContainsSelectTable() {
		List<Long> crfIds = getSession().createCriteria(EForm.class).add(Restrictions.eq("active", true))
				.setProjection(Projections.property("id")).list();
		if (crfIds.size() > 0) {
			List<Long> sectionIds = getSession().createCriteria(EFormSection.class).add(Restrictions.eq("active", true))
					.add(Restrictions.in("eform.id", crfIds)).setProjection(Projections.property("id")).list();
			if (sectionIds.size() > 0) {
				List<Long> sectionIds2 = getSession().createCriteria(EFormSectionElement.class)
						.add(Restrictions.in("section.id", sectionIds)).add(Restrictions.eq("type", "selectTable"))
						.setProjection(Projections.property("section.id")).list();
				if (sectionIds2.size() > 0) {
					List<EForm> crfs = getSession().createCriteria(EFormSection.class).add(Restrictions.in("id", crfIds))
							.setProjection(Projections.property("eform")).list();
					return crfs;
				}

			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EFormMapplingTable> findAllMappingTables() {
		List<EFormMapplingTable> list = getSession().createCriteria(EFormMapplingTable.class).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EFormMapplingTableColumnMap> allCrfMapplingTableColumnMap() {
		List<EFormMapplingTableColumnMap> list = getSession().createCriteria(EFormMapplingTableColumnMap.class).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EFormSectionElement> sectionElemetsSelectTale(Long id) {
		List<Long> mapedIds = getSession().createCriteria(EFormMapplingTableColumnMap.class)
				.setProjection(Projections.property("element.id")).list();
		List<EFormSectionElement> list = new ArrayList<>();
		EForm crf = (EForm) getSession().get(EForm.class, id);
		Hibernate.initialize(crf.getSections());
		for (EFormSection sec : crf.getSections()) {
			Criteria cri = getSession().createCriteria(EFormSectionElement.class).add(Restrictions.eq("section", sec))
					.add(Restrictions.eq("type", "selectTable"));
			if (mapedIds.size() > 0)
				cri.add(Restrictions.not(Restrictions.in("id", mapedIds)));
			List<EFormSectionElement> eles = cri.list();
			list.addAll(eles);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EFormMapplingTableColumn> eformMappingTableColumns(Long id) {
		List<EFormMapplingTableColumn> list = getSession().createCriteria(EFormMapplingTableColumn.class)
				.add(Restrictions.eq("mappingTable.id", id)).list();
		return list;
	}

	@Override
	public EFormMapplingTableColumnMap saveCrfMapplingTableColumnMap(EFormMapplingTableColumnMap map) {
		getSession().save(map);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EFormEleCaliculation> eformEleCaliculationList() {
		return getSession().createCriteria(EFormEleCaliculation.class).list();
	}

	@Override
	public EForm crfByName(String value) {
		EForm crf = (EForm) getSession().createCriteria(EForm.class).add(Restrictions.eq("name", value))
				.add(Restrictions.eq("active", true)).uniqueResult();
		return crf;
	}

	@Override
	public void saveEFormEleCaliculationList(List<EFormEleCaliculation> list) {
		for (EFormEleCaliculation cec : list) {
			getSession().save(cec);
		}
		
	}

	@Override
	public UserMaster getUserMasterWithName(String username) {
		UserMaster user = (UserMaster) getSession().createCriteria(UserMaster.class).add(Restrictions.eq("username", username)).uniqueResult();
		return user;
	}

	@Override
	public void updateEForm(EForm crf) {
		getSession().update(crf);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EFormRule> findAllEFormRules() {
		return getSession().createCriteria(EFormRule.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EForm> findAllEFormWithSubElemens() {
		List<EForm> ml = getSession().createCriteria(EForm.class).list();
		for (EForm crf : ml) {
			Hibernate.initialize(crf.getSections());
			for (EFormSection sec : crf.getSections()) {
				Hibernate.initialize(sec.getElement());
				if (sec.getElement() == null || sec.getElement().size() == 0) {
					List<EFormSectionElement> element = getSession().createCriteria(EFormSectionElement.class)
							.add(Restrictions.eq("section", sec)).list();
					sec.setElement(element);
					for (EFormSectionElement ele : sec.getElement()) {
						Hibernate.initialize(ele.getElementValues());
						if (ele.getElementValues() == null || ele.getElementValues().size() == 0) {
							List<EFormSectionElementValue> v = getSession().createCriteria(EFormSectionElementValue.class)
									.add(Restrictions.eq("sectionElement", ele)).list();
							ele.setElementValues(v);
						}
					}
				} else {
					for (EFormSectionElement ele : sec.getElement()) {
						Hibernate.initialize(ele.getElementValues());
					}
				}
				Hibernate.initialize(sec.getGroup());
				EFormGroup group = sec.getGroup();
				if (group != null) {
					Hibernate.initialize(group.getElement());
					if (group.getElement() == null || group.getElement().size() == 0) {
						List<EFormGroupElement> geles = getSession().createCriteria(EFormGroupElement.class)
								.add(Restrictions.eq("group", group)).list();
						group.setElement(geles);
						for (EFormGroupElement ge : group.getElement()) {
							List<EFormGroupElementValue> elementValues = getSession()
									.createCriteria(EFormGroupElementValue.class).add(Restrictions.eq("groupElement", ge))
									.list();
							ge.setElementValues(elementValues);
						}
					} else {
						for (EFormGroupElement ele : group.getElement()) {
							Hibernate.initialize(ele.getElementValues());
						}
					}
				}
			}
		}

		return ml;
	}

	@Override
	public String saveEFormRule(EFormRule rule) {
		try {
			getSession().save(rule);
			for (EFormRuleWithOther o : rule.getOtherCrf()) {
				getSession().save(o);
			}
			return rule.getName();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public EFormRule eformRule(Long id) {
		return (EFormRule) getSession().createCriteria(EFormRule.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public void updateEFormRule(EFormRule rule) {
		getSession().update(rule);
		
	}

	@Override
	public String eformVisibilityChangeStatus(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EFormVisibility eformVisibility(Long id) {
		return (EFormVisibility) getSession().get(EFormVisibility.class, id);
	}

	@Override
	public void updateUpdateVisibility(EFormVisibility rule) {
		getSession().update(rule);
		
	}

}
