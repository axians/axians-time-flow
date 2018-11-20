/*-
 * Copyright 2017-2018 Axians SAIV S.p.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
-*/
package it.axians.vaadin.flow.component.time;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.JsonSerializable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

/**
 * Flow component for <code>&lt;axians-time&gt;</code>.
 */
@Tag("axians-time")
@HtmlImport("frontend://bower_components/axians-time/axians-time.html")
public class AxiansTime extends PolymerTemplate<AxiansTime.Model>
        implements HasStyle {

    /**
     * The Class FormatStep.
     */
    public static class FormatStep implements JsonSerializable {

        private long diff;

        private boolean relative;

        private String format;

        /**
         * Instantiates a new format step.
         *
         * @param duration
         *            the minimum amount of time between current time and
         *            timestamp for which the format should be used
         * @param format
         *            the format
         */
        public FormatStep(Duration duration, String format) {
            this.diff = duration.toMillis();
            this.format = format;
        }

        /**
         * Instantiates a new format step.
         *
         * @param duration
         *            the minimum amount of time between current time and
         *            timestamp for which the display should be relative
         */
        public FormatStep(Duration duration) {
            this.diff = duration.toMillis();
            this.relative = true;
        }

        @Override
        public JsonObject toJson() {
            JsonObject json = Json.createObject();
            json.put("diff", this.diff);
            json.put("relative", this.relative);
            json.put("format", this.format != null ? Json.create(this.format)
                    : Json.createNull());
            return json;
        }

        @Override
        public JsonSerializable readJson(JsonObject value) {
            this.diff = (int) value.getNumber("diff");
            this.relative = value.getBoolean("relative");
            this.format = value.getString("format");
            return this;
        }
    }

    /**
     * Model for the template.
     */
    public static interface Model extends TemplateModel {

        /**
         * Returns the timestamp.
         *
         * @return the timestamp
         */
        String getTimestamp();

        /**
         * Sets the timestamp.
         *
         * @param timestmap
         *            the new timestamp
         */
        void setTimestamp(String timestmap);

        /**
         * Returns the locale.
         *
         * @return the locale
         */
        String getLocale();

        /**
         * Sets the locale.
         *
         * @param locale
         *            the new locale
         */
        void setLocale(String locale);

        /**
         * Returns the format.
         *
         * @return the format
         */
        String getFormat();

        /**
         * Sets the format.
         *
         * @param format
         *            the new format
         */
        void setFormat(String format);

        /**
         * Returns the update.
         *
         * @return the update
         */
        boolean isUpdate();

        /**
         * Sets the update.
         *
         * @param update
         *            the new update
         */
        void setUpdate(boolean update);

        /**
         * Returns the relative.
         *
         * @return the relative
         */
        boolean isRelative();

        /**
         * Sets the relative.
         *
         * @param relative
         *            the new relative
         */
        void setRelative(boolean relative);

        int getNowThreshold();

        void setNowThreshold(int nowThreshold);

    }

    /**
     * Creates a new <code>AxiansTime</code> component.
     */
    public AxiansTime() {
        this(Instant.now());
    }

    /**
     * Creates a new <code>AxiansTime</code> component with specific timestamp.
     *
     * @param timestamp
     *            the timestamp
     */
    public AxiansTime(Instant timestamp) {
        this(timestamp, "LLL");
    }

    /**
     * Creates a new <code>AxiansTime</code> component with specific timestamp
     * and format.
     *
     * @param timestamp
     *            the timestamp
     * @param format
     *            the format
     */
    public AxiansTime(Instant timestamp, String format) {
        this(timestamp, format, Locale.ENGLISH);
    }

    /**
     * Creates a new <code>AxiansTime</code> component with specific timestamp,
     * format and locale.
     *
     * @param timestamp
     *            the timestamp
     * @param format
     *            the format
     * @param locale
     *            the locale
     */
    public AxiansTime(Instant timestamp, String format, Locale locale) {
        this.setTimestamp(timestamp);
        this.setFormat(format);
        this.setLocale(locale);
    }

    /**
     * Returns the timestamp.
     *
     * @return the timestamp
     */
    public Instant getTimestamp() {
        String timestamp = this.getModel().getTimestamp();
        return Instant.parse(timestamp);
    }

    /**
     * Sets the timestamp.
     *
     * @param timestamp
     *            the new timestamp
     */
    public void setTimestamp(Instant timestamp) {
        String iso8601 = timestamp.toString();
        this.getModel().setTimestamp(iso8601);
    }

    /**
     * Returns the locale.
     *
     * @return the locale
     */
    @Override
    public Locale getLocale() {
        String locale = this.getModel().getLocale();
        return Locale.forLanguageTag(locale);
    }

    /**
     * Sets the locale.
     *
     * @param locale
     *            the new locale
     */
    public void setLocale(Locale locale) {
        this.getModel().setLocale(locale.toLanguageTag());
    }

    /**
     * Returns the format.
     *
     * @return the format
     */
    public String getFormat() {
        return this.getModel().getFormat();
    }

    /**
     * Sets the format.
     *
     * @param format
     *            the new format
     */
    public void setFormat(String format) {
        this.getModel().setFormat(format);
    }

    /**
     * Checks if is update.
     *
     * @return true, if is update
     */
    public boolean isUpdate() {
        return this.getModel().isUpdate();
    }

    /**
     * Sets the update.
     *
     * @param update
     *            the new update
     */
    public void setUpdate(boolean update) {
        this.getModel().setUpdate(update);
    }

    /**
     * Checks if is relative.
     *
     * @return true, if is relative
     */
    public boolean isRelative() {
        return this.getModel().isRelative();
    }

    /**
     * Sets the relative.
     *
     * @param relative
     *            the new relative
     */
    public void setRelative(boolean relative) {
        this.getModel().setRelative(relative);
    }

    /**
     * Gets the now threshold.
     *
     * @return the now threshold
     */
    public int getNowThreshold() {
        return this.getModel().getNowThreshold();
    }

    /**
     * Sets the now threshold.
     *
     * @param nowThreshold
     *            the new now threshold
     */
    public void setNowThreshold(int nowThreshold) {
        this.getModel().setNowThreshold(nowThreshold);
    }

    /**
     * Gets the format steps.
     *
     * @return the format steps
     */
    public List<FormatStep> getFormatSteps() {
        JsonArray stepsJsonArray = (JsonArray) this.getElement()
                .getPropertyRaw("formatSteps");
        List<FormatStep> steps = new ArrayList<>();
        for (int i = 0; i < stepsJsonArray.length(); i++) {
            steps.add(stepsJsonArray.get(i));
        }
        return steps;
    }

    /**
     * Sets the format steps.
     *
     * @param formatSteps
     *            the new format steps
     */
    public void setFormatSteps(List<FormatStep> formatSteps) {
        AtomicInteger index = new AtomicInteger();
        this.getElement().setPropertyJson("formatSteps",
                formatSteps.stream().map(FormatStep::toJson).collect(
                        Json::createArray,
                        (arr, value) -> arr.set(index.getAndIncrement(), value),
                        (arr, arrOther) -> {
                            int startIndex = arr.length();
                            for (int i = 0; i < arrOther.length(); i++) {
                                JsonValue value = arrOther.get(i);
                                arr.set(startIndex + i, value);
                            }
                        }));
    }

    /**
     * Sets the format steps.
     *
     * @param formatSteps
     *            the new format steps
     */
    public void setFormatSteps(FormatStep... formatSteps) {
        this.setFormatSteps(List.of(formatSteps));
    }

}
