/**
 * 
 */
package com.trusteer.pniel.service.events;

import java.util.List;

/**
 * @author Pniel Abramovich
 *
 */
public interface EventManager {

	void publish(List<String> address);
}
