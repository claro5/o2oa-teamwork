package com.x.teamwork.assemble.control.jaxrs.tasktag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.x.base.core.project.cache.CacheManager;
import org.apache.commons.lang3.StringUtils;

import com.x.base.core.entity.JpaObject;
import com.x.base.core.project.annotation.FieldDescribe;
import com.x.base.core.project.bean.WrapCopier;
import com.x.base.core.project.bean.WrapCopierFactory;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.jaxrs.WoId;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;
import com.x.teamwork.core.entity.Dynamic;
import com.x.teamwork.core.entity.TaskTag;

public class ActionDelete extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(ActionDelete.class);

	protected ActionResult<Wo> execute(HttpServletRequest request, EffectivePerson effectivePerson, String taskTagId ) throws Exception {
		ActionResult<Wo> result = new ActionResult<>();
		TaskTag taskTag = null;
		Boolean check = true;
		Wo wo = new Wo();
		Dynamic dynamic = null;
		
		if ( StringUtils.isEmpty( taskTagId ) ) {
			check = false;
			Exception exception = new TaskTagIdForQueryEmptyException();
			result.error( exception );
		}

		if( Boolean.TRUE.equals( check ) ){
			try {
				taskTag = taskTagQueryService.get(taskTagId);
				if ( taskTag == null) {
					check = false;
					Exception exception = new TaskTagNotExistsException(taskTagId);
					result.error( exception );
				}
			} catch (Exception e) {
				check = false;
				Exception exception = new TaskTagQueryException(e, "根据指定flag查询工作任务标签信息对象时发生异常。taskTagId:" + taskTagId);
				result.error(exception);
				logger.error(e, effectivePerson, request, null);
			}
		}
		
		if( Boolean.TRUE.equals( check ) ){
			try {
				taskTagPersistService.delete( taskTagId, effectivePerson );				
				// 更新缓存
				CacheManager.notify( TaskTag.class );
				
				wo.setId( taskTag.getId() );
				
			} catch (Exception e) {
				check = false;
				Exception exception = new TaskTagQueryException(e, "根据指定flag删除工作任务标签信息对象时发生异常。taskTagId:" + taskTagId);
				result.error(exception);
				logger.error(e, effectivePerson, request, null);
			}
		}		
				
		/*if( Boolean.TRUE.equals( check ) ){
			try {					
				dynamic = dynamicPersistService.taskTagDeleteDynamic( taskTag, effectivePerson );	
				if( dynamic != null ) {
					List<WoDynamic> dynamics = new ArrayList<>();
					dynamics.add( WoDynamic.copier.copy( dynamic ) );
					wo.setDynamics(dynamics);
				}
			} catch (Exception e) {
				logger.error(e, effectivePerson, request, null);
			}	
		}*/
		result.setData( wo );
		return result;
	}

	public static class Wo extends WoId {
		
		@FieldDescribe("操作引起的动态内容")
		List<WoDynamic> dynamics = new ArrayList<>();

		public List<WoDynamic> getDynamics() {
			return dynamics;
		}

		public void setDynamics(List<WoDynamic> dynamics) {
			this.dynamics = dynamics;
		}
	}
	
	public static class WoDynamic extends Dynamic{

		private static final long serialVersionUID = -5076990764713538973L;

		public static WrapCopier<Dynamic, WoDynamic> copier = WrapCopierFactory.wo( Dynamic.class, WoDynamic.class, null, JpaObject.FieldsInvisible);
		
		private Long rank = 0L;

		public Long getRank() {
			return rank;
		}

		public void setRank(Long rank) {
			this.rank = rank;
		}		
	}
}