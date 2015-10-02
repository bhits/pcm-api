package gov.samhsa.consent2share.infrastructure.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Convenience builder class to build
 * {@code List<Supplier<ReportParameterConfigurerTask>>} from
 * {@link ReportParameterConfigurerTask} instances. The
 * {@link ReportParameterConfigurerTaskAdder#build} methods returns a
 * {@link Collections#unmodifiableList} of the collected
 * {@link ReportParameterConfigurerTask}s.
 */
public class ReportParameterConfigurerChainBuilder {

	/**
	 * Add a new {@link ReportParameterConfigurerTask} instance to the chain
	 * builder.
	 *
	 * @param task
	 *            the task
	 * @return the report parameter configurer task adder
	 */
	public static ReportParameterConfigurerTaskAdder add(
			Supplier<ReportParameterConfigurerTask> task) {
		final ReportParameterConfigurerTaskAdder adder = new ReportParameterConfigurerChainBuilder().new ReportParameterConfigurerTaskAdder();
		return adder.add(task);
	}

	/**
	 * Convenience method to create a new empty {@link List} of
	 * {@code List<Supplier<ReportParameterConfigurerTask>>}.
	 *
	 * @return the list
	 */
	private static final List<Supplier<ReportParameterConfigurerTask>> newList() {
		return new ArrayList<>();
	}

	/**
	 * Convenience method to create a new {@link List} of
	 * {@code List<Supplier<ReportParameterConfigurerTask>>} filled with given
	 * tasks.
	 *
	 * @param tasks
	 *            the tasks
	 * @return the list
	 */
	private static final List<Supplier<ReportParameterConfigurerTask>> newList(
			List<Supplier<ReportParameterConfigurerTask>> tasks) {
		return new ArrayList<>(tasks);
	}

	/**
	 * The Class ReportParameterConfigurerTaskAdder.
	 */
	public class ReportParameterConfigurerTaskAdder {

		/** The tasks. */
		private final List<Supplier<ReportParameterConfigurerTask>> tasks;

		/**
		 * Instantiates a new report parameter configurer task adder.
		 */
		public ReportParameterConfigurerTaskAdder() {
			this.tasks = newList();
		}

		/**
		 * Instantiates a new report parameter configurer task adder. Adds the
		 * new task to the given tasks.
		 *
		 * @param tasks
		 *            the tasks
		 * @param task
		 *            the task
		 */
		public ReportParameterConfigurerTaskAdder(
				List<Supplier<ReportParameterConfigurerTask>> tasks,
				Supplier<ReportParameterConfigurerTask> task) {
			this.tasks = newList(tasks);
			this.tasks.add(task);
		}

		/**
		 * Adds the new task.
		 *
		 * @param task
		 *            the task
		 * @return the report parameter configurer task adder
		 */
		public ReportParameterConfigurerTaskAdder add(
				Supplier<ReportParameterConfigurerTask> task) {
			return new ReportParameterConfigurerTaskAdder(this.tasks, task);
		}

		/**
		 * Builds the report parameter configurer chain as type of
		 * {@code List<Supplier<ReportParameterConfigurerTask>>} and wraps it
		 * with {@link Collections#unmodifiableList}.
		 *
		 * @return the unmodifiable list for report parameter configurer chain
		 */
		public List<Supplier<ReportParameterConfigurerTask>> build() {
			return Collections.unmodifiableList(this.tasks);
		}
	}

}
