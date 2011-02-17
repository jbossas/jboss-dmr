/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.dmr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;

/**
 * Base64 encoding/decoding utilities.
 * 
 * @author Jonathan Pearlin
 */
public class Base64 {

    /**
     * Encoding padding character to fill empty bytes at the end of the sequence.
     */
    private static final char PADDING_CHARACTER = '=';

    /**
     * Dictionary of Base64 encoding characters.
     */
    private static final String BASE_64_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    /**
     * Encoding block size of bytes used to encode byte data into Base64.
     */
    private static final int ENCODE_BLOCK_SIZE_BYTES = 3;

    /**
     * Decoding block size of bytes required to decode a Base64 string back into byte data.
     */
    private static final int DECODE_BLOCK_SIZE_BYTES = 4;

    /**
     * Encodes the supplied byte array into a Base64 encoded string. Note that this implementation does NOT insert newline
     * characters every 72 bytes.
     * 
     * @param bytes A byte array of data to be encoded.
     * @return The Base64 encoded string representation of the bytes or {@code null} if the data cannot be encoded.
     * @throws IOException if an error occurs during encoding.
     */
    public static final String encode(final byte[] bytes) throws IOException {
        if (bytes != null) {
            return new String(encodeBytes(bytes));
        } else {
            return null;
        }
    }

    /**
     * Decodes a Base64 encoded string into a byte array of the originally encoded values. Note that this implementation does
     * NOT handle decoding additional newline characters added during the encoding process every 72 bytes.
     * 
     * @param base64 A Base64 encoded string that represents binary data.
     * @return A byte array containing the originally encoded binary data or {@code null} if the data cannot be decoded.
     * @throws IOException if an error occurs during decoding.
     */
    public static final byte[] decode(final String base64) throws IOException {
        if (base64 != null) {
            return decodeBytes(base64.getBytes());
        } else {
            return null;
        }
    }

    /**
     * Decodes an array of bytes that represents Base64 encoded characters into the original byte array of binary data.
     * 
     * @param bytes A byte array representing Base64 encoded characters.
     * @return The original byte array of binary data.
     * @throws IOException if an error occurs during decoding.
     */
    private static final byte[] decodeBytes(final byte[] bytes) throws IOException {
        final DataInputStream dis = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(bytes)));
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(baos));
        final byte[] currentBlock = new byte[DECODE_BLOCK_SIZE_BYTES];
        byte[] decodedBytes = null;

        try {
            // Continue to read bytes from the stream until none are available.
            while (dis.available() > 0) {
                dos.write(decodeBlock(dis, currentBlock));
            }
            dos.flush();
            decodedBytes = baos.toByteArray();
        } finally {
            dis.close();
            dos.close();
        }

        return decodedBytes;
    }

    /**
     * Encodes the supplied byte data into a byte array of Base64 encoded data.
     * 
     * @param bytes Binary data to be encoded into Base64.
     * @return A byte array containing Base64 encoded data.
     * @throws IOException if an error occurs during encoding.
     */
    private static final byte[] encodeBytes(final byte[] bytes) throws IOException {
        final DataInputStream dis = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(bytes)));
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(baos));
        byte[] currentBlock = new byte[ENCODE_BLOCK_SIZE_BYTES];
        byte[] encodedBytes = null;

        try {
            // Continue to read bytes from the stream until none are available.
            while (dis.available() > 0) {
                dos.write(encodeBlock(dis, currentBlock));
            }
            dos.flush();
            encodedBytes = baos.toByteArray();
        } finally {
            dis.close();
            dos.close();
        }

        return encodedBytes;
    }

    /**
     * Encodes the next block of data read from the supplied input stream and writes the encoded value to the supplied output
     * stream.
     * 
     * @param dis The source of the data to be encoded.
     * @param buffer Buffer used to hold the data while performing the encoding.
     * @throws IOException if an error occurs while attempting to read, or encode the data.
     */
    private static byte[] encodeBlock(final DataInputStream dis, final byte[] buffer) throws IOException {
        int numBytesRead = 0;

        /*
         * If there is a full block (or more) available, grab the next block for encoding. Otherwise, we will need to add
         * padding, so determine just how many bytes can be read into the current block.
         */
        if (dis.available() >= ENCODE_BLOCK_SIZE_BYTES) {
            dis.readFully(buffer);
            numBytesRead = buffer.length;
        } else {
            /*
             * Clear the unread bytes from the previous read before attempting read the remaining bytes. This ensures that there
             * are not any left over bytes from the previous read affecting the padding of the last block.
             */
            clearBuffer(buffer, ENCODE_BLOCK_SIZE_BYTES);
            numBytesRead = dis.read(buffer);
        }

        // Encode the block as Base 64
        return encodeData(buffer, numBytesRead);
    }

    /**
     * Encodes a block of byte data into base 64 encoded data.
     * 
     * @param block The block of data to be encoded.
     * @param numberOfBytes The number of bytes read during the encoding process for this block. This value is important to
     *        determine if special padding is required.
     * @throws IOException if an error occurs while attempting to encode the data.
     */
    private static byte[] encodeData(final byte[] block, final int numberOfBytes) throws IOException {
        int convertedIndex = 0;
        int currentIndex = 0;
        final byte[] encodedBuffer = new byte[DECODE_BLOCK_SIZE_BYTES];

        if (block != null) {
            while (currentIndex < block.length && convertedIndex < DECODE_BLOCK_SIZE_BYTES && currentIndex < numberOfBytes) {
                switch (convertedIndex) {
                    case 0:
                        // Left 6 bits of the first byte
                        encodedBuffer[convertedIndex] = ((byte) BASE_64_CHARACTERS
                                .codePointAt(((block[currentIndex] >> 2) & 0x3f)));
                        break;
                    case 1:
                        // Right 2 bits of first byte OR'ed with left 4 bits of second byte
                        byte secondByte = (byte) ((block[currentIndex] & 0x3) << 4);
                        currentIndex++;
                        encodedBuffer[convertedIndex] = ((byte) BASE_64_CHARACTERS
                                .codePointAt((secondByte | ((block[currentIndex] & 0xf0) >> 4))));
                        break;
                    case 2:
                        // Right 4 bits of second byte OR'ed with left 2 bits of third byte
                        byte thirdByte = (byte) (((byte) (block[currentIndex]) & 0xf) << 2);
                        currentIndex++;
                        encodedBuffer[convertedIndex] = ((byte) BASE_64_CHARACTERS
                                .codePointAt((thirdByte | ((block[currentIndex] & 0xc0) >> 6))));
                        break;
                    case 3:
                        // Right 6 bits of the third byte
                        encodedBuffer[convertedIndex] = ((byte) BASE_64_CHARACTERS.codePointAt((block[currentIndex]) & 0x3f));
                        currentIndex++;
                        break;
                }
                convertedIndex++;
            }
        }

        // If padding is required, add the padding character to the output for this block.
        for (int i = convertedIndex; i < DECODE_BLOCK_SIZE_BYTES; i++) {
            encodedBuffer[i] = (byte) PADDING_CHARACTER;
        }

        return encodedBuffer;
    }

    /**
     * Decodes the next block of data read from the supplied input stream and writes the decoded value to the supplied output
     * stream.
     * 
     * @param dis The source of the data to be decoded.
     * @param buffer Buffer used to hold the data while performing the decoding.
     * @throws IOException if an error occurs while attempting to read, or decode the data.
     */
    private static byte[] decodeBlock(final DataInputStream dis, final byte[] buffer) throws IOException {
        /*
         * Read one block (4 bytes) of Base 64 encoded data at a time and decode to the original byte values, removing any
         * padding present if necessary.
         */
        dis.readFully(buffer);
        return decodeData(translateBlock(buffer));
    }

    /**
     * Decodes a block of base 64 encoded data.
     * 
     * @param block The block of data to be decoded.
     * @throws IOException if an error happens during decoding of the data.
     */
    private static byte[] decodeData(final byte[] block) throws IOException {
        int currentIndex = 0;
        int shiftAmount = 2;
        int mask = 0x3;
        final byte[] decodedBuffer = new byte[findActualEncodedBlockSize(block)];

        if (block != null) {
            while (currentIndex < decodedBuffer.length) {
                byte currentByte = (byte) (block[currentIndex] << shiftAmount);
                switch (currentIndex) {
                    case 0:
                        decodedBuffer[currentIndex] = ((byte) (currentByte | ((block[currentIndex + 1] >> (shiftAmount + 2)) & mask)));
                        break;
                    case 1:
                        decodedBuffer[currentIndex] = ((byte) (currentByte | ((block[currentIndex + 1] >> (shiftAmount - 2)) & mask)));
                        break;
                    case 2:
                        decodedBuffer[currentIndex] = ((byte) (currentByte | (block[currentIndex + 1] & mask)));
                        break;
                }

                shiftAmount += 2;
                mask = ((mask << 2) | 0x3);
                currentIndex++;
            }
        }

        return decodedBuffer;
    }

    /**
     * Determines how many actual bytes are contained in the block of encoded data. This method is needed to skip the padding
     * bytes added during the encoding process.
     * 
     * @param block A block of base 64 encoded data.
     * @return The actual number of encoded bytes found in the block, minus any padding bytes.
     */
    private static int findActualEncodedBlockSize(final byte[] block) {
        int i;
        for (i = 0; i < block.length; i++) {
            if (block[i] == -1) {
                i--; // Reset to the previous, valid index
                break;
            }
        }

        return i < ENCODE_BLOCK_SIZE_BYTES ? i : ENCODE_BLOCK_SIZE_BYTES;
    }

    /**
     * Clears the requested amount of bytes from the supplied buffer by setting the value at the index to zero. This method
     * assumes that the values to be cleared are at the end of the array, so it starts at the index at the length of the buffer
     * minus the number to clear.
     * 
     * @param buffer The buffer to be cleared.
     * @param numberToClear The number of bytes to clear.
     */
    private static void clearBuffer(final byte[] buffer, final int numberToClear) {
        // Clear the unread bytes from a previous read.
        for (int i = buffer.length - numberToClear; i < buffer.length; i++) {
            buffer[i] = 0;
        }
    }

    /**
     * Looks up the byte value that was translated into a Base64 encoded character. For instance, if the byte value 65 ('A') is
     * found, the value 0 will be put in its place. See the {@code BASE_64_CHARACTERS} constant for the actual order.
     * <b>NOTE</b>: if the padding character is found ('='), it will be replaced with -1 to denote a removable byte.
     * 
     * @param block A block of encoded Base64 bytes to be translated.
     * @return A byte array containing the translated byte values.
     */
    private static final byte[] translateBlock(final byte[] block) {
        final byte[] translatedBlock = new byte[DECODE_BLOCK_SIZE_BYTES];

        for (int i = 0; i < DECODE_BLOCK_SIZE_BYTES; i++) {
            /*
             * Note that the padding character ('='), will cause a value of -1 to be put into the byte array. This will be
             * detected later on by the decoding process and recognized as a padding byte and will be removed from the decoded
             * value.
             */
            translatedBlock[i] = (byte) BASE_64_CHARACTERS.indexOf((char) block[i]);
        }

        return translatedBlock;
    }

    /**
     * Base 64 input stream that supports both the encoding and decoding of the wrapped input stream.
     */
    public static final class InputStream extends FilterInputStream {

        /**
         * The operation to be performed on the data in the stream.
         */
        private final Operations operation;

        /**
         * Temporary buffer used to store intermediate results.
         */
        private byte[] tempBuffer;

        /**
         * The current position within the temporary buffer. This is used to keep track of which byte to "read" next.
         */
        private int position = -1;

        /**
         * Creates a new base 64 input stream wrapped around the supplied java.io.InputStream instance.
         * 
         * @param is A java.io.InputStream stream of data.
         */
        public InputStream(final java.io.InputStream is) {
            this(is, Operations.DECODE);
        }

        /**
         * Creates a new base 64 input stream wrapped around the supplied java.io.InputStream instance that performs the
         * requested operation on the data.
         * 
         * @param is A java.io.InputStream stream of data.
         * @param operation The operation to be performed on the data.
         */
        public InputStream(final java.io.InputStream is, final Operations operation) {
            super(is);
            this.operation = operation;
        }

        @Override
        public int read() throws IOException {
            /*
             * If there is no data waiting to be processed in the temporary buffer, it's time to go get more data.
             */
            if (position < 0) {
                // Make sure that we have not read beyond the end of the wrapped stream.
                if (in.available() > 0) {
                    if (operation == Operations.ENCODE) {
                        tempBuffer = encodeBlock(new DataInputStream(in), new byte[ENCODE_BLOCK_SIZE_BYTES]);
                    } else {
                        tempBuffer = decodeBlock(new DataInputStream(in), new byte[DECODE_BLOCK_SIZE_BYTES]);
                    }
                    position = 0;
                } else {
                    // Set to -1 to force and EOF to be returned.
                    position = -1;
                }
            }

            /*
             * If the position pointer is somehow beyond the length of the buffer or has been set to -1, return EOF. Otherwise,
             * read the next byte from the temporary buffer.
             */
            if (position >= tempBuffer.length || position < 0) {
                return -1;
            } else {
                final byte b = tempBuffer[position];
                position++;
                if (position >= tempBuffer.length) {
                    position = -1;
                }

                // Mask with 0xFF to convert to unsigned
                return b & 0xFF;
            }
        }

        @Override
        public int read(byte[] b) throws IOException {
            return read(b, 0, b.length);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int i = 0;

            for (i = 0; i < len; i++) {
                final int value = read();
                if (value >= 0) {
                    b[off + i] = (byte) value;
                } else if (i == 0) {
                    return -1;
                } else {
                    break;
                }
            }

            return i;
        }
    }

    /**
     * Base 64 input stream that supports both the encoding and decoding of the wrapped input stream.
     */
    public static final class OutputStream extends FilterOutputStream {

        /**
         * The operation to be performed on the data in the stream.
         */
        private final Operations operation;

        /**
         * Temporary buffer used to store intermediate results.
         */
        private byte[] tempBuffer;

        /**
         * The current position within the temporary buffer. This is used to determine if enough bytes have been accumulated to
         * be written.
         */
        private int position = 0;

        /**
         * Creates a new base 64 output stream wrapped around the supplied java.io.OutputStream instance.
         * 
         * @param os A java.io.OutputStream stream of data.
         */
        public OutputStream(final java.io.OutputStream os) {
            this(os, Operations.ENCODE);
        }

        /**
         * Creates a new base 64 output stream wrapped around the supplied java.io.OutputStream instance.
         * 
         * @param os A java.io.OutputStream stream of data.
         * @param operation The operation to be performed on the data.
         */
        public OutputStream(final java.io.OutputStream os, final Operations operation) {
            super(os);
            this.operation = operation;
            tempBuffer = new byte[(operation == Operations.ENCODE ? ENCODE_BLOCK_SIZE_BYTES : DECODE_BLOCK_SIZE_BYTES)];
        }

        @Override
        public void write(final int b) throws IOException {
            tempBuffer[position] = (byte) b;
            position++;
            if (position >= tempBuffer.length) {
                if (operation == Operations.ENCODE) {
                    out.write(encodeData(tempBuffer, tempBuffer.length));
                } else {
                    out.write(decodeData(tempBuffer));
                }
                position = 0;
                // Clear the temporary buffer to ensure it is clean for the next block.
                clearBuffer(tempBuffer, tempBuffer.length);
            }
        }

        @Override
        public void write(byte[] b) throws IOException {
            write(b, 0, b.length);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            for (int i = 0; i < len; i++) {
                write(b[off + i]);
            }
        }

        /**
         * Closes this stream by writing any pending data to the underlying stream. NOTE: THIS METHOD DOES NOT CLOSE THE
         * UNDERLYING STREAM. You must close the underlying stream manually.
         */
        public void close() throws IOException {
            finish();
        }

        /**
         * Performs any final clean-up/writing to ensure ALL data has been output to the wrapped output stream. This method
         * handles edge cases in the base 64 specification, such as padding, etc.
         * 
         * @throws IOException if an error occurs during final output.
         */
        public void finish() throws IOException {
            /*
             * This block is required to catch the case where the last block written to the stream to be encoded is not 3 bytes
             * (i.e., it requires padding). This block will catch that case and flush the remaining bytes to ensure ALL data is
             * written out when the operation is ENCODE.
             */
            if (operation == Operations.ENCODE && position < ENCODE_BLOCK_SIZE_BYTES) {
                // Flush the remaining bytes!
                out.write(encodeData(tempBuffer, position));
            }
        }
    }

    /**
     * Enumeration representing the operations that can be performed by the input and output streams.
     */
    public static enum Operations {
        DECODE, ENCODE;
    }
}
