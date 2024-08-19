# spring-and-mockito
Projeto para demostrar boas práticas no uso do Mockito com Spring

## References
https://customer.cloudamqp.com/login

https://www.youtube.com/watch?v=_Uo14nxB_iA

https://www.youtube.com/watch?v=NQ3fZtyXji0

## technologies
* Codium
* Refact.ai
* Cursor.ai
* Spring Boot
* Spring Data JPA
* H2 Database
* RabbitMQ


Mockito is a popular mocking framework for unit tests in Java. It allows you to create mock objects and define their behavior, making isolating the code under test easier. Here are some of the best features of Mockito:

- Creating Mocks: You can create mock objects of a class or an interface using the Mockito.mock() method.
List mockedList = Mockito.mock(List.class);
- Stubbing Methods: You can define the behavior of a mock object's method using the when().thenReturn() pattern.
when(mockedList.get(0)).thenReturn("first element");
- Verifying Interactions: Mockito allows you to verify that certain methods were called on the mock object.
mockedList.add("one");
Mockito.verify(mockedList).add("one");
- Argument Matchers: You can use argument matchers to specify flexible argument matching.
when(mockedList.get(anyInt())).thenReturn("element");
- Spying on Real Objects: You can create a spy of a real object to partially mock its behavior.
List list = new LinkedList();
List spy = Mockito.spy(list);
when(spy.size()).thenReturn(100);
- Capturing Arguments: Mockito provides the ArgumentCaptor class to capture arguments passed to mock methods.
ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
mockedList.add("one");
Mockito.verify(mockedList).add(argumentCaptor.capture());
assertEquals("one", argumentCaptor.getValue());
- Throwing Exceptions: You can stub methods to throw exceptions.
when(mockedList.get(0)).thenThrow(new RuntimeException());
- InOrder Verification: Mockito allows you to verify the order of method calls.
InOrder inOrder = Mockito.inOrder(mockedList);
inOrder.verify(mockedList).add("first");
inOrder.verify(mockedList).add("second");
- Timeout Verification: You can verify that a method was called within a specific time frame.
Mockito.verify(mockedList, Mockito.timeout(100)).add("one");
- Annotations: Mockito provides annotations to simplify the creation and injection of mocks.
@Mock
List mockedList;

@InjectMocks
SomeClass someClass;

@Before
public void initMocks() {
    MockitoAnnotations.initMocks(this);
}
Here's a simple example that demonstrates some of these features:

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.List;

public class MockitoExampleTest {

    @Mock
    List<String> mockedList;

    @InjectMocks
    SomeClass someClass;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMockitoFeatures() {
        // Stubbing
        when(mockedList.get(0)).thenReturn("first element");

        // Using mock object
        String element = mockedList.get(0);
        assertEquals("first element", element);

        // Verifying interaction
        mockedList.add("one");
        verify(mockedList).add("one");

        // Argument captor
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockedList).add(argumentCaptor.capture());
        assertEquals("one", argumentCaptor.getValue());
    }
}
These features make Mockito a powerful tool for writing unit tests in Java.
