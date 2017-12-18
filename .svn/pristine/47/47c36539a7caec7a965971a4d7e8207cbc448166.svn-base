
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationSystem extends DomainEntity {

	private int	minimumPeriodActive;


	@Min(0)
	public int getMinimumPeriodActive() {
		return minimumPeriodActive;
	}

	public void setMinimumPeriodActive(int minimumPeriodActive) {
		this.minimumPeriodActive = minimumPeriodActive;
	}
}
