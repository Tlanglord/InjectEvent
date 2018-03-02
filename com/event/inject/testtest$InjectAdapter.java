import com.event.inject.test;

import com.event.inject.test.test;
import com.event.inject.adapter.InjectAdapter;

public class test$InjectAdapter implements InjectAdapter<test> {

	public void injects(test target) {
		target.ssString = 111111;
	}

}