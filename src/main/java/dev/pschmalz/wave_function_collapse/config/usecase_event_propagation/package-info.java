/**
 * Enforces the Dependency Rule (Clean Architecture)<br>
 * => Usecases must not depend upon the configuration layer (Spring)<br>
 *  AND Infrastructure components should not depend upon Spring<br>
 * => That is, they must not use ApplicationEvent or ApplicationListener objects (comes from Spring)<br>
 * => So there needs to be an application listener (Spring) that propagates the events to the actual listeners (custom)<br>
 */
package dev.pschmalz.wave_function_collapse.config.usecase_event_propagation;