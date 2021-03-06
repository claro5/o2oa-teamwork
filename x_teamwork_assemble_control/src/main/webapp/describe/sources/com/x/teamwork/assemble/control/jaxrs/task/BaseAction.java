package com.x.teamwork.assemble.control.jaxrs.task;

import com.x.base.core.project.annotation.FieldDescribe;
import com.x.base.core.project.cache.Cache;
import com.x.base.core.project.jaxrs.StandardJaxrsAction;
import com.x.teamwork.assemble.control.service.*;
import com.x.teamwork.core.entity.Task;
import com.x.teamwork.core.entity.TaskView;

public class BaseAction extends StandardJaxrsAction {
	
	protected Cache.CacheCategory taskCache = new Cache.CacheCategory( Task.class );
	
	protected Cache.CacheCategory taskViewCache = new Cache.CacheCategory( TaskView.class );
	
	protected 	TaskQueryService taskQueryService = new TaskQueryService();
	
	protected 	TaskTagQueryService taskTagQueryService = new TaskTagQueryService();
	
	protected 	TaskTagPersistService taskTagPersistService = new TaskTagPersistService();
	
	protected 	ProjectQueryService projectQueryService = new ProjectQueryService();
	
	protected 	CustomExtFieldReleQueryService customExtFieldReleQueryService = new CustomExtFieldReleQueryService();
	
	protected 	TaskPersistService taskPersistService = new TaskPersistService();
	
	protected TaskListPersistService taskListPersistService = new TaskListPersistService();

	protected TaskListQueryService taskListQueryService = new TaskListQueryService();
	
	protected TaskGroupQueryService taskGroupQueryService = new TaskGroupQueryService();
	
	protected TaskGroupPersistService taskGroupPersistService = new TaskGroupPersistService();
	
	protected TaskViewQueryService taskViewQueryService = new TaskViewQueryService();
	
	protected 	DynamicPersistService dynamicPersistService = new DynamicPersistService();
	
	public static class TaskListChange {
		
		@FieldDescribe("转移前的列表ID")
		private String source;
		
		@FieldDescribe("转移后的列表ID")
		private String target;
		
		public TaskListChange() {}
		
		public TaskListChange( String _source, String _target ) {
			this.source = _source;
			this.target = _target;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}
	}
	
}
