package es.laboral.desin.componentes;

import javafx.event.Event;
import javafx.event.EventType;

public class LimitEvent extends Event {
	private static final long serialVersionUID = 1L;
// Tipo de evento propio: cuelga de Event.ANY
	public static final EventType<LimitEvent> LIMIT_REACHED = new EventType<>(Event.ANY, "LIMIT_REACHED");
// Número máximo de caracteres que permite el campo en ese momento
	private final int maxLength;
// Texto que el usuario intenta escribir o pegar y que el componente rechaza
	private final String discarded;

	public LimitEvent(EventType<? extends LimitEvent> eventType, int maxLength, String discarded) {
		super(eventType);
		this.maxLength = maxLength;
		this.discarded = discarded;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public String getDiscarded() {
		return discarded;
	}
}