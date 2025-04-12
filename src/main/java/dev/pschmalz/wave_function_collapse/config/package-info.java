/**
 * Mostly defines for Spring how to construct its beans (<code>@Bean</code> inside <code>@Configuration</code> classes)<br>
 * <br>
 * Values necessary for configuration are found inside classpath:/config.yml at runtime<br>
 * Inside the codebase it's directly in the 'resources' Source Folder<br>
 * <br>
 * Spring starts SmartLifecycle beans (bean.start()) automatically<br>
 * @see dev.pschmalz.wave_function_collapse.config.lifecycle
 */
package dev.pschmalz.wave_function_collapse.config;