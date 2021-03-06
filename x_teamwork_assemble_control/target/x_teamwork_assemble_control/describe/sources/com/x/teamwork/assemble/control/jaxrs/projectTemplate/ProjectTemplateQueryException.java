package com.x.teamwork.assemble.control.jaxrs.projectTemplate;

import com.x.base.core.project.exception.PromptException;

class ProjectTemplateQueryException extends PromptException {

	private static final long serialVersionUID = 1859164370743532895L;

	ProjectTemplateQueryException( Throwable e ) {
		super("系统在查询项目模板信息时发生异常。" , e );
	}
	
	ProjectTemplateQueryException( Throwable e, String message ) {
		super("系统在查询项目模板信息时发生异常。Message:" + message, e );
	}
}
