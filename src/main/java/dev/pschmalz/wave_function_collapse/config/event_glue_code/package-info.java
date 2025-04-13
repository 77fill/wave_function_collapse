/**
 * Motivation:
 * <pre>
 *     Desired relationships (from abstract to less abstract):
 *          Usecase Layer:
 *              SpecificUsecase fires Usecase.Event (e.g. finished loading images!)
 *              SpecificUsecase needs EventPublisher(*) for this
 *          Infrastructure Layer:
 *              View should be a Listener(*) of Usecase.Event
 *                  (e.g. finished loading images? now show something!)
 *          Configuration Layer (Spring):
 *              EventPublisher comes from here (ApplicationEventPublisher(*) in Spring)
 *              Listener comes from here (ApplicationListener(*) in Spring)
 *              SpringFramework brings events to listeners automatically
 * </pre>
 * Problem: Dependency Rule is violated! (Clean Architecture)
 * <pre>
 *     More precisely:
 *          * Infrastructure depends on Configuration (View -> ApplicationListener)
 *          * Usecase depends on Configuration (SpecificUsecase -> ApplicationEventPublisher)
 * </pre>
 * Solution by this package:
 * <pre>
 *          Usecase Layer:
 *              SpecificUsecase fires Usecase.Event (e.g. finished loading images!)
 *              SpecificUsecase uses custom EventPublisher (just a Consumer of Usecase.Event)
 *                  => no extra dependencies! just a java Consumer!
 *          Infrastructure Layer:
 *              View is a Usecase.Listener
 *                  => depends on Usecase Layer; that's ok (less abstract depends on more abstract)
 *          Configuration Layer (Spring):
 *              SpringFramework (only) manages ApplicationEventPublishers and ApplicationListeners
 *              Glue code: custom event publisher uses ApplicationEventPublisher
 *              Glue code: ApplicationListener delegates to Usecase.Listeners
 * </pre>
 */
@Deprecated
package dev.pschmalz.wave_function_collapse.config.event_glue_code;