package com.mos.bsd.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description Rmi单据
 * @author odin
 * @date 2017年5月11日 上午11:21:55
 */
public class RmiBill {

	/** 主单 */
	private List<RmiBillMain> mains = new ArrayList<>();
	/** 明细 */
	private List<RmiBillSub> subs = new ArrayList<>();

	/**
	 * 合并
	 * 
	 * @param rmiBill
	 */
	public void merge(RmiBill rmiBill) {
		this.mains.addAll(rmiBill.getMains());
		this.subs.addAll(rmiBill.getSubs());
	}

	/**
	 * @return 获取主单
	 */
	public List<RmiBillMain> getMains() {
		return mains;
	}

	/**
	 * @param 设置主单
	 */
	public void setMains(List<RmiBillMain> mains) {
		this.mains = mains;
	}

	/**
	 * @return 获取明细
	 */
	public List<RmiBillSub> getSubs() {
		return subs;
	}

	/**
	 * @param 设置明细
	 */
	public void setSubs(List<RmiBillSub> subs) {
		this.subs = subs;
	}

}
