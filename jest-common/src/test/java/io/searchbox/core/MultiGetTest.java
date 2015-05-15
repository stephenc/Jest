package io.searchbox.core;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Dogukan Sonmez
 */
public class MultiGetTest {

    Doc doc1 = new Doc("twitter", "tweet", "1");
    Doc doc2 = new Doc("twitter", "tweet", "2");
    Doc doc3 = new Doc("twitter", "tweet", "3");

    @Test
    public void getMultipleDocs() {
        MultiGet get = new MultiGet.Builder.ByDoc(Arrays.asList(doc1, doc2, doc3)).build();

        assertEquals("GET", get.getRestMethodName());
        assertEquals("/_mget", get.getURI());
        assertEquals("{\"docs\":[" +
                "{\"_type\":\"tweet\",\"_id\":\"1\",\"_index\":\"twitter\"}," +
                "{\"_type\":\"tweet\",\"_id\":\"2\",\"_index\":\"twitter\"}," +
                "{\"_type\":\"tweet\",\"_id\":\"3\",\"_index\":\"twitter\"}]}", get.getData(new Gson()));
    }

    @Test
    public void equalsReturnsTrueForSameDocs() {
        MultiGet multiGet1 = new MultiGet.Builder.ByDoc(Arrays.asList(doc1, doc2, doc3)).build();
        MultiGet multiGet1Duplicate = new MultiGet.Builder.ByDoc(Arrays.asList(doc1, doc2, doc3)).build();

        assertEquals(multiGet1, multiGet1Duplicate);
    }

    @Test
    public void equalsReturnsFalseForDiffererntDocs() {
        MultiGet multiGet1 = new MultiGet.Builder.ByDoc(Arrays.asList(doc1, doc3)).build();
        MultiGet multiGet2 = new MultiGet.Builder.ByDoc(Arrays.asList(doc2, doc3)).build();

        assertNotEquals(multiGet1, multiGet2);
    }

    @Test
    public void getDocumentWithMultipleIds() {
        MultiGet get = new MultiGet.Builder.ById("twitter", "tweet").addId(Arrays.asList("1", "2", "3")).build();

        assertEquals("GET", get.getRestMethodName());
        assertEquals("twitter/tweet/_mget", get.getURI());
        assertEquals("{\"ids\":[\"1\",\"2\",\"3\"]}", get.getData(new Gson()));
    }

    @Test
    public void equalsReturnsTrueForSameIds() {
        MultiGet multiGet1 = new MultiGet.Builder.ById("twitter", "tweet").addId(Arrays.asList("1", "2", "3")).build();
        MultiGet multiGet1Dupliacte = new MultiGet.Builder.ById("twitter", "tweet").addId(Arrays.asList("1", "2", "3")).build();

        assertEquals(multiGet1, multiGet1Dupliacte);
    }

    @Test
    public void equalsReturnsFalseForDifferentIds() {
        MultiGet multiGet1 = new MultiGet.Builder.ById("twitter", "tweet").addId(Arrays.asList("1", "2", "3")).build();
        MultiGet multiGet2 = new MultiGet.Builder.ById("twitter", "tweet").addId(Arrays.asList("1", "9", "3")).build();

        assertNotEquals(multiGet1, multiGet2);
    }

}
