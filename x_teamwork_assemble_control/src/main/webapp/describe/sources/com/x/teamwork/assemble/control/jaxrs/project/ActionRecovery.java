package com.x.teamwork.assemble.control.jaxrs.project;

import javax.servlet.http.HttpServletRequest;

import com.x.base.core.project.cache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.jaxrs.WoId;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;
import com.x.teamwork.core.entity.Project;

public class ActionRecovery extends BaseAction {

	private static  Logger logger = LoggerFactory.getLogger(ActionRecovery.class);

	protected ActionResult<Wo> execute(HttpServletRequest request, EffectivePerson effectivePerson, String projectId) throws Exception {
		ActionResult<Wo> result = new ActionResult<>();
		Project project = null;
		Boolean check = true;
		
		if ( StringUtils.isEmpty( projectId )) {
			check = false;
			Exception exception = new ProjectFlagForQueryEmptyException();
			result.error( exception );
		}
		
		if( Boolean.TRUE.equals( check ) ){
			try {
				project = projectQueryService.get( projectId );
				if ( project == null) {
					check = false;
					Exception exception = new ProjectNotExistsException( projectId );
					result.error( exception );
				}
			} catch (Exception e) {
				check = false;
				Exception exception = new ProjectQueryException(e, "根据指定flag查询应用项目信息对象时发生异常。ID:" + projectId );
				result.error(exception);
				logger.error(e, effectivePerson, request, null);
			}
		}
		
		if( Boolean.TRUE.equals( check ) ){
			try {	
				projectPersistService.recoveryProject( projectId);

				// 更新缓存
				CacheManager.notify( Project.class );
				Wo wo = new Wo();
				wo.setId( project.getId() );
				result.setData( wo );
			} catch (Exception e) {
				check = false;
				Exception exception = new ProjectPersistException(e, "项目恢复时发生异常。");
				result.error(exception);
				logger.error(e, effectivePerson, request, null);
			}
			
			try {					
				dynamicPersistService.projectRecoveryDynamic( project, effectivePerson);
			} catch (Exception e) {
				logger.error(e, effectivePerson, request, null);
			}
		}
		return result;
	}	

	public static class Wo extends WoId {
	}
	
}