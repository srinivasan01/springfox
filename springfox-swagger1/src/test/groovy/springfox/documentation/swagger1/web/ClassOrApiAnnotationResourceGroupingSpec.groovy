/*
 *
 *  Copyright 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package springfox.documentation.swagger1.web

import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import spock.lang.Specification
import spock.lang.Unroll
import springfox.documentation.spring.web.mixins.RequestMappingSupport
import springfox.documentation.swagger.web.ClassOrApiAnnotationResourceGrouping

@Mixin(RequestMappingSupport)
class ClassOrApiAnnotationResourceGroupingSpec extends Specification {

  @Unroll
  def "group paths and descriptions"() {
    given:
      RequestMappingInfo requestMappingInfo = requestMappingInfo('/anything')
      ClassOrApiAnnotationResourceGrouping strategy = new ClassOrApiAnnotationResourceGrouping()
      def group = strategy.getResourceGroups(requestMappingInfo, handlerMethod).first()


    expect:
      group.groupName == groupName
      group.position == position
      strategy.getResourceDescription(requestMappingInfo, handlerMethod) == description

    where:
      handlerMethod                                    | groupName              | description                    | position
      dummyHandlerMethod()                             | "dummy-class"          | "Dummy Class"                  | 0
      dummyControllerHandlerMethod()                   | "group-name"           | "Group name"                   | 2
      dummyControllerWithApiDescriptionHandlerMethod() | "group-name"           | "Dummy Controller Description" | 2
      petServiceHandlerMethod()                        | "pet-service"          | "Operations about pets"        | 0
      multipleRequestMappingsHandlerMethod()           | "pet-grooming-service" | "Grooming operations for pets" | 0


  }
}
