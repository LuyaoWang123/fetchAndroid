package lw.intern.fetch;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(RobolectricTestRunner.class)
public class DataFetcherTest {

    @Mock
    private DataFetcher dataFetcher;
    private MockWebServer mockWebServer;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockWebServer = new MockWebServer();
        Context context = RuntimeEnvironment.getApplication();
        dataFetcher = new DataFetcher(context);
    }

    @Test
    public void fetchData_success_callsOnDataFetched() throws Exception {
        // Arrange
        String mockResponse = "[{\"id\":1, \"listId\":10, \"name\":\"Item 1\"}]";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponse));
        CountDownLatch latch = new CountDownLatch(1);

        DataFetcher.DataResponseListener mockListener = mock(DataFetcher.DataResponseListener.class);
        ArgumentCaptor<List<ItemInterface>> argumentCaptor = forClass(List.class);

        // Act
        String url = mockWebServer.url("/").toString();
        dataFetcher.fetchData(url, mockListener);

        // Wait for the async response
        latch.await(2, TimeUnit.SECONDS);

        // Assert
        verify(mockListener, times(1)).onDataFetched(argumentCaptor.capture());
        assertTrue("Data was not fetched", argumentCaptor.getValue() != null);
        latch.countDown();
    }

    @Test
    public void fetchData_fail_ExceptionThrown() throws Exception {
        //Arrange, a piece of data lacking id
        // Arrange
        String mockResponse = "[{\"listId\":10, \"name\":\"Item 1\"}]";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponse));
        final Exception[] capturedException = new Exception[1];
        CountDownLatch latch = new CountDownLatch(1);
        DataFetcher.DataResponseListener mockListener = new DataFetcher.DataResponseListener() {
            @Override
            public void onDataFetched(List<ItemInterface> data) {
                // not expected in this test
            }

            @Override
            public void onError(Exception exception) {
                capturedException[0] = exception;
                latch.countDown();
            }
        };

        // Act
        String url = mockWebServer.url("/").toString();
        dataFetcher.fetchData(url, mockListener);

        // Wait for async response
        latch.await(2, TimeUnit.SECONDS);

        // Assert
        assertNotNull("Expected exception was not thrown", capturedException[0]);
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}
