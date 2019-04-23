package com.github.borisskert.aramalltimetable.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

class CloneUtilsTest {

    @Test
    public void shouldBeEqualAfterClone() throws Exception {
        TestObject object = new TestObject(
                "my string",
                1234,
                123.123,
                1235L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string",
                        4321,
                        52.72
                ),
                null
        );


        TestObject cloned = CloneUtils.deepClone(object);

        assertThat(cloned, is(equalTo(object)));
    }

    @Test
    public void shouldNotBeModifiedIfOriginIsModified() throws Exception {
        TestObject object = new TestObject(
                "my string",
                1234,
                123.123,
                1235L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string",
                        4321,
                        52.72
                ),
                null
        );


        TestObject cloned = CloneUtils.deepClone(object);

        object.setStringProperty("my string 2");

        assertThat(cloned, is(not(equalTo(object))));
    }

    @Test
    public void shouldNotBeModifiedIfInnerObjectIsModified() throws Exception {
        TestObject object = new TestObject(
                "my string",
                1234,
                123.123,
                1235L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string",
                        4321,
                        52.72
                ),
                null
        );


        TestObject cloned = CloneUtils.deepClone(object);

        object.getInnerTestObjectProperty().setStringProperty("my string 2");

        assertThat(cloned, is(not(equalTo(object))));
    }

    @Test
    public void shouldCloneDifferentTypes() throws Exception {
        TestObject object = new TestObject(
                "my string",
                1234,
                123.123,
                1235L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string",
                        4321,
                        52.72
                ),
                null
        );


        OtherTestObject cloned = CloneUtils.deepClone(object, OtherTestObject.class);

        assertThat(object.equals(cloned), is(true));
    }

    @Test
    public void shouldPatch() throws Exception {
        TestObject origin = new TestObject(
                "my string",
                1234,
                123.123,
                1235L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string",
                        4321,
                        52.72
                ),
                null
        );

        TestObject patch = new TestObject(
                "my string 1",
                null,
                null,
                1235L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string 1",
                        4322,
                        null
                ),
                null
        );

        TestObject cloned = CloneUtils.deepClone(origin);

        TestObject patched = CloneUtils.deepPatch(origin, patch);

        assertThat(patched.getStringProperty(), is(equalTo(patch.getStringProperty())));
        assertThat(patched.getIntegerProperty(), is(equalTo(origin.getIntegerProperty())));
        assertThat(patched.getDoubleProperty(), is(equalTo(origin.getDoubleProperty())));
        assertThat(patched.getLongProperty(), is(equalTo(patch.getLongProperty())));
        assertThat(patched.getLocalDateProperty(), is(nullValue()));
        assertThat(patched.getLocalDateTimeProperty(), is(nullValue()));

        TestObject.InnerTestObject patchedInnerObject = patched.getInnerTestObjectProperty();
        assertThat(patchedInnerObject.getStringProperty(), is(equalTo(patch.getInnerTestObjectProperty().getStringProperty())));
        assertThat(patchedInnerObject.getIntegerProperty(), is(equalTo(patch.getInnerTestObjectProperty().getIntegerProperty())));
        assertThat(patchedInnerObject.getDoubleProperty(), is(equalTo(origin.getInnerTestObjectProperty().getDoubleProperty())));

        assertThat(origin, is(equalTo(cloned)));
    }

    @Test
    public void shouldDeepEqual() throws Exception {
        TestObject origin = new TestObject(
                "my string",
                1234,
                123.123,
                1235L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string",
                        4321,
                        52.72
                ),
                null
        );

        TestObject other = new TestObject(
                "my string",
                1234,
                123.123,
                1235L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string",
                        4321,
                        52.72
                ),
                null
        );

        boolean areEqual = CloneUtils.deepEquals(origin, other);

        assertThat(areEqual, is(true));
    }

    @Test
    public void shouldNotDeepEqual() throws Exception {
        TestObject origin = new TestObject(
                "my string",
                1234,
                123.123,
                1234L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string",
                        4321,
                        52.72
                ),
                null
        );

        TestObject other = new TestObject(
                "my string",
                1234,
                123.123,
                1235L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string",
                        4321,
                        52.72
                ),
                null
        );

        boolean areEqual = CloneUtils.deepEquals(origin, other);

        assertThat(areEqual, is(false));
    }

    @Test
    public void shouldPatchListProperties() throws Exception {
        List<String> originStringList = new ArrayList<>();
        originStringList.add("value in string list");

        TestObject origin = new TestObject(
                "my string",
                1234,
                123.123,
                1234L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string",
                        4321,
                        52.72
                ),
                originStringList
        );

        List<String> patchStringList = new ArrayList<>();
        patchStringList.add("patched value in string list");

        TestObject patch = new TestObject(
                "my string patched",
                1234,
                123.123,
                1234L,
                null,
                null,
                new TestObject.InnerTestObject(
                        "my other string",
                        4321,
                        52.72
                ),
                patchStringList
        );

        TestObject patched = CloneUtils.patch(origin, patch, TestObject.class);

        List<String> patchedStringList = patched.getStringList();
        assertThat(patchedStringList, hasSize(1));
        assertThat(patchedStringList.get(0), is(equalTo("patched value in string list")));
    }

    public static class TestObject {
        private String stringProperty;
        private Integer integerProperty;
        private Double doubleProperty;
        private Long longProperty;
        private LocalDate localDateProperty;
        private LocalDateTime localDateTimeProperty;
        private InnerTestObject innerTestObjectProperty;

        private List<String> stringList;

        public TestObject(
                @JsonProperty("stringProperty") String stringProperty,
                @JsonProperty("integerProperty") Integer integerProperty,
                @JsonProperty("doubleProperty") Double doubleProperty,
                @JsonProperty("longProperty") Long longProperty,
                @JsonProperty("localDateProperty") LocalDate localDateProperty,
                @JsonProperty("localDateTimeProperty") LocalDateTime localDateTimeProperty,
                @JsonProperty("innerTestObjectProperty") InnerTestObject innerTestObjectProperty,
                @JsonProperty("stringList") List<String> stringList
        ) {
            this.stringProperty = stringProperty;
            this.integerProperty = integerProperty;
            this.doubleProperty = doubleProperty;
            this.longProperty = longProperty;
            this.localDateProperty = localDateProperty;
            this.localDateTimeProperty = localDateTimeProperty;
            this.innerTestObjectProperty = innerTestObjectProperty;
            this.stringList = stringList;
        }

        public String getStringProperty() {
            return stringProperty;
        }

        public Integer getIntegerProperty() {
            return integerProperty;
        }

        public Double getDoubleProperty() {
            return doubleProperty;
        }

        public Long getLongProperty() {
            return longProperty;
        }

        public LocalDate getLocalDateProperty() {
            return localDateProperty;
        }

        public LocalDateTime getLocalDateTimeProperty() {
            return localDateTimeProperty;
        }

        public InnerTestObject getInnerTestObjectProperty() {
            return innerTestObjectProperty;
        }

        public void setStringProperty(String stringProperty) {
            this.stringProperty = stringProperty;
        }

        public void setIntegerProperty(Integer integerProperty) {
            this.integerProperty = integerProperty;
        }

        public void setDoubleProperty(Double doubleProperty) {
            this.doubleProperty = doubleProperty;
        }

        public void setLongProperty(Long longProperty) {
            this.longProperty = longProperty;
        }

        public void setLocalDateProperty(LocalDate localDateProperty) {
            this.localDateProperty = localDateProperty;
        }

        public void setLocalDateTimeProperty(LocalDateTime localDateTimeProperty) {
            this.localDateTimeProperty = localDateTimeProperty;
        }

        public void setInnerTestObjectProperty(InnerTestObject innerTestObjectProperty) {
            this.innerTestObjectProperty = innerTestObjectProperty;
        }

        public List<String> getStringList() {
            return stringList;
        }

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestObject that = (TestObject) o;

            if (stringProperty != null ? !stringProperty.equals(that.stringProperty) : that.stringProperty != null)
                return false;
            if (integerProperty != null ? !integerProperty.equals(that.integerProperty) : that.integerProperty != null)
                return false;
            if (doubleProperty != null ? !doubleProperty.equals(that.doubleProperty) : that.doubleProperty != null)
                return false;
            if (longProperty != null ? !longProperty.equals(that.longProperty) : that.longProperty != null)
                return false;
            if (localDateProperty != null ? !localDateProperty.equals(that.localDateProperty) : that.localDateProperty != null)
                return false;
            if (localDateTimeProperty != null ? !localDateTimeProperty.equals(that.localDateTimeProperty) : that.localDateTimeProperty != null)
                return false;
            return innerTestObjectProperty != null ? innerTestObjectProperty.equals(that.innerTestObjectProperty) : that.innerTestObjectProperty == null;
        }

        public boolean equals(OtherTestObject that) {
            if (that == null) return false;

            if (stringProperty != null ? !stringProperty.equals(that.getStringProperty()) : that.getStringProperty() != null)
                return false;
            if (integerProperty != null ? !integerProperty.equals(that.getIntegerProperty()) : that.getIntegerProperty() != null)
                return false;
            if (doubleProperty != null ? !doubleProperty.equals(that.getDoubleProperty()) : that.getDoubleProperty() != null)
                return false;
            if (longProperty != null ? !longProperty.equals(that.getLongProperty()) : that.getLongProperty() != null)
                return false;
            if (localDateProperty != null ? !localDateProperty.equals(that.getLocalDateProperty()) : that.getLocalDateProperty() != null)
                return false;
            if (localDateTimeProperty != null ? !localDateTimeProperty.equals(that.getLocalDateTimeProperty()) : that.getLocalDateTimeProperty() != null)
                return false;
            return innerTestObjectProperty != null ? innerTestObjectProperty.equals(that.getInnerTestObjectProperty()) : that.getInnerTestObjectProperty() == null;
        }

        @Override
        public int hashCode() {
            int result = stringProperty != null ? stringProperty.hashCode() : 0;
            result = 31 * result + (integerProperty != null ? integerProperty.hashCode() : 0);
            result = 31 * result + (doubleProperty != null ? doubleProperty.hashCode() : 0);
            result = 31 * result + (longProperty != null ? longProperty.hashCode() : 0);
            result = 31 * result + (localDateProperty != null ? localDateProperty.hashCode() : 0);
            result = 31 * result + (localDateTimeProperty != null ? localDateTimeProperty.hashCode() : 0);
            result = 31 * result + (innerTestObjectProperty != null ? innerTestObjectProperty.hashCode() : 0);
            return result;
        }

        static class InnerTestObject {
            private String stringProperty;
            private Integer integerProperty;
            private Double doubleProperty;


            public InnerTestObject(
                    @JsonProperty("stringProperty") String stringProperty,
                    @JsonProperty("integerProperty") Integer integerProperty,
                    @JsonProperty("doubleProperty") Double doubleProperty
            ) {
                this.stringProperty = stringProperty;
                this.integerProperty = integerProperty;
                this.doubleProperty = doubleProperty;
            }

            public String getStringProperty() {
                return stringProperty;
            }

            public Integer getIntegerProperty() {
                return integerProperty;
            }

            public Double getDoubleProperty() {
                return doubleProperty;
            }

            public void setStringProperty(String stringProperty) {
                this.stringProperty = stringProperty;
            }

            public void setIntegerProperty(Integer integerProperty) {
                this.integerProperty = integerProperty;
            }

            public void setDoubleProperty(Double doubleProperty) {
                this.doubleProperty = doubleProperty;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                InnerTestObject that = (InnerTestObject) o;

                if (stringProperty != null ? !stringProperty.equals(that.stringProperty) : that.stringProperty != null)
                    return false;
                if (integerProperty != null ? !integerProperty.equals(that.integerProperty) : that.integerProperty != null)
                    return false;
                return doubleProperty != null ? doubleProperty.equals(that.doubleProperty) : that.doubleProperty == null;
            }

            public boolean equals(OtherTestObject.InnerTestObject that) {
                if (that == null) return false;

                if (stringProperty != null ? !stringProperty.equals(that.getStringProperty()) : that.getStringProperty() != null)
                    return false;
                if (integerProperty != null ? !integerProperty.equals(that.getIntegerProperty()) : that.getIntegerProperty() != null)
                    return false;
                return doubleProperty != null ? doubleProperty.equals(that.getDoubleProperty()) : that.getDoubleProperty() == null;
            }

            @Override
            public int hashCode() {
                int result = stringProperty != null ? stringProperty.hashCode() : 0;
                result = 31 * result + (integerProperty != null ? integerProperty.hashCode() : 0);
                result = 31 * result + (doubleProperty != null ? doubleProperty.hashCode() : 0);
                return result;
            }
        }
    }

    public static class OtherTestObject {
        private final String stringProperty;
        private final Integer integerProperty;
        private final Double doubleProperty;
        private final Long longProperty;
        private final LocalDate localDateProperty;
        private final LocalDateTime localDateTimeProperty;
        private final InnerTestObject innerTestObjectProperty;

        public OtherTestObject(
                @JsonProperty("stringProperty") String stringProperty,
                @JsonProperty("integerProperty") Integer integerProperty,
                @JsonProperty("doubleProperty") Double doubleProperty,
                @JsonProperty("longProperty") Long longProperty,
                @JsonProperty("localDateProperty") LocalDate localDateProperty,
                @JsonProperty("localDateTimeProperty") LocalDateTime localDateTimeProperty,
                @JsonProperty("innerTestObjectProperty") InnerTestObject innerTestObjectProperty
        ) {
            this.stringProperty = stringProperty;
            this.integerProperty = integerProperty;
            this.doubleProperty = doubleProperty;
            this.longProperty = longProperty;
            this.localDateProperty = localDateProperty;
            this.localDateTimeProperty = localDateTimeProperty;
            this.innerTestObjectProperty = innerTestObjectProperty;
        }

        public String getStringProperty() {
            return stringProperty;
        }

        public Integer getIntegerProperty() {
            return integerProperty;
        }

        public Double getDoubleProperty() {
            return doubleProperty;
        }

        public Long getLongProperty() {
            return longProperty;
        }

        public LocalDate getLocalDateProperty() {
            return localDateProperty;
        }

        public LocalDateTime getLocalDateTimeProperty() {
            return localDateTimeProperty;
        }

        public InnerTestObject getInnerTestObjectProperty() {
            return innerTestObjectProperty;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OtherTestObject that = (OtherTestObject) o;

            if (stringProperty != null ? !stringProperty.equals(that.stringProperty) : that.stringProperty != null)
                return false;
            if (integerProperty != null ? !integerProperty.equals(that.integerProperty) : that.integerProperty != null)
                return false;
            if (doubleProperty != null ? !doubleProperty.equals(that.doubleProperty) : that.doubleProperty != null)
                return false;
            if (longProperty != null ? !longProperty.equals(that.longProperty) : that.longProperty != null)
                return false;
            if (localDateProperty != null ? !localDateProperty.equals(that.localDateProperty) : that.localDateProperty != null)
                return false;
            if (localDateTimeProperty != null ? !localDateTimeProperty.equals(that.localDateTimeProperty) : that.localDateTimeProperty != null)
                return false;
            return innerTestObjectProperty != null ? innerTestObjectProperty.equals(that.innerTestObjectProperty) : that.innerTestObjectProperty == null;
        }

        @Override
        public int hashCode() {
            int result = stringProperty != null ? stringProperty.hashCode() : 0;
            result = 31 * result + (integerProperty != null ? integerProperty.hashCode() : 0);
            result = 31 * result + (doubleProperty != null ? doubleProperty.hashCode() : 0);
            result = 31 * result + (longProperty != null ? longProperty.hashCode() : 0);
            result = 31 * result + (localDateProperty != null ? localDateProperty.hashCode() : 0);
            result = 31 * result + (localDateTimeProperty != null ? localDateTimeProperty.hashCode() : 0);
            result = 31 * result + (innerTestObjectProperty != null ? innerTestObjectProperty.hashCode() : 0);
            return result;
        }

        static class InnerTestObject {
            private final String stringProperty;
            private final Integer integerProperty;
            private final Double doubleProperty;


            public InnerTestObject(
                    @JsonProperty("stringProperty") String stringProperty,
                    @JsonProperty("integerProperty") Integer integerProperty,
                    @JsonProperty("doubleProperty") Double doubleProperty
            ) {
                this.stringProperty = stringProperty;
                this.integerProperty = integerProperty;
                this.doubleProperty = doubleProperty;
            }

            public String getStringProperty() {
                return stringProperty;
            }

            public Integer getIntegerProperty() {
                return integerProperty;
            }

            public Double getDoubleProperty() {
                return doubleProperty;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                InnerTestObject that = (InnerTestObject) o;

                if (stringProperty != null ? !stringProperty.equals(that.stringProperty) : that.stringProperty != null)
                    return false;
                if (integerProperty != null ? !integerProperty.equals(that.integerProperty) : that.integerProperty != null)
                    return false;
                return doubleProperty != null ? doubleProperty.equals(that.doubleProperty) : that.doubleProperty == null;
            }

            @Override
            public int hashCode() {
                int result = stringProperty != null ? stringProperty.hashCode() : 0;
                result = 31 * result + (integerProperty != null ? integerProperty.hashCode() : 0);
                result = 31 * result + (doubleProperty != null ? doubleProperty.hashCode() : 0);
                return result;
            }
        }
    }
}